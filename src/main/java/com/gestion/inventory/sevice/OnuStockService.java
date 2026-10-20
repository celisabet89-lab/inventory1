package com.gestion.inventory.sevice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Categoria;
import com.gestion.inventory.domain.Onu;
import com.gestion.inventory.domain.Product;
import com.gestion.inventory.domain.UnidadMedida;
import com.gestion.inventory.repository.CategoriaRepository;
import com.gestion.inventory.repository.ProductRepository;
import com.gestion.inventory.repository.UnidadMedidaRepository;

/**
 * Mantiene el stock del producto (ONU/router) sincronizado con las ONUs registradas.
 * El producto se busca por nombre (articulo) igual a la marca de la ONU.
 */
@Service
public class OnuStockService {

    private static final String CATEGORIA = "onus";
    private static final String UNIDAD_ABREV = "UND";
    private static final String UNIDAD_NOMBRE = "Unidad";

    @Autowired ProductRepository productRepository;
    @Autowired CategoriaRepository categoriaRepository;
    @Autowired UnidadMedidaRepository unidadMedidaRepository;
    @Autowired StockSyncService stockSync;

    private String limpiar(String s) {
        return s == null ? "" : s.trim();
    }

    /** Suma 1 al producto de la marca; si no existe lo crea (nombre, cantidad, categoria y unidad). */
    public Product sumar(String marca) {
        String nombre = limpiar(marca);
        if (nombre.isEmpty()) return null;
        Product p = productRepository.findFirstByArticuloIgnoreCase(nombre).orElse(null);
        if (p == null) {
            p = new Product();
            p.setArticulo(nombre);
            p.setDescripcion(nombre);
            p.setUnidad(0);
            p.setPrecio(BigDecimal.ZERO);
            p.setCategoria(categoria());
            p.setUnidadMedida(unidad());
        }
        p.setUnidad((p.getUnidad() == null ? 0 : p.getUnidad()) + 1);
        Product guardado = productRepository.save(p);
        stockSync.desdeProducto(guardado);
        return guardado;
    }

    /** Resta 1 (sin bajar de 0) al producto de la marca, si existe. */
    public void restar(String marca) {
        String nombre = limpiar(marca);
        if (nombre.isEmpty()) return;
        productRepository.findFirstByArticuloIgnoreCase(nombre).ifPresent(p -> {
            int actual = p.getUnidad() == null ? 0 : p.getUnidad();
            p.setUnidad(Math.max(0, actual - 1));
            stockSync.desdeProducto(productRepository.save(p));
        });
    }

    /** Si la marca cambió al editar una ONU, mueve la cantidad de un producto al otro. */
    public Product cambioMarca(String anterior, String nueva) {
        if (limpiar(anterior).equalsIgnoreCase(limpiar(nueva))) return null;
        restar(anterior);
        return sumar(nueva);
    }

    private Categoria categoria() {
        return categoriaRepository.findFirstByNombreIgnoreCase(CATEGORIA).orElseGet(() -> {
            Categoria c = new Categoria();
            c.setNombre(CATEGORIA);
            return categoriaRepository.save(c);
        });
    }

    private UnidadMedida unidad() {
        return unidadMedidaRepository.findFirstByAbreviaturaIgnoreCase(UNIDAD_ABREV).orElseGet(() -> {
            UnidadMedida u = new UnidadMedida();
            u.setNombre(UNIDAD_NOMBRE);
            u.setAbreviatura(UNIDAD_ABREV);
            return unidadMedidaRepository.save(u);
        });
    }

    public void vincular(Onu onu, Product p) {
        if (p != null) onu.setProduct(p);
    }
}