// useCase/producto/UpdateProduct.java (Versión Corregida)
package com.gestion.inventory.useCase.product;

import com.gestion.inventory.domain.Categoria;
import com.gestion.inventory.domain.Product;
import com.gestion.inventory.domain.UnidadMedida;
import com.gestion.inventory.domain.dtorecust.ProductRequest;
import com.gestion.inventory.repository.CategoriaRepository;
import com.gestion.inventory.repository.ProductRepository;
import com.gestion.inventory.repository.UnidadMedidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateProduc {

    private final ProductRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UnidadMedidaRepository unidadMedidaRepository;

    @Transactional
    public Product execute(Long id, ProductRequest request) {
        // Buscar producto existente
        Product producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product no encontrado con ID: " + id));

        if (request.getArticulo() != null) producto.setArticulo(request.getArticulo());
        if (request.getDescripcion() != null) producto.setDescripcion(request.getDescripcion());
        if (request.getUnidad() != null) producto.setUnidad(request.getUnidad());
        if (request.getPrecio() != null) producto.setPrecio(request.getPrecio());
        if (request.getFoto() != null) producto.setFoto(request.getFoto());
        if (request.getCodigoBarras() != null) producto.setCodigoBarras(request.getCodigoBarras());

        // Actualizar categoría
        if (request.getCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(request.getCategoria())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + request.getCategoria()));
            producto.setCategoria(categoria);
        }

        // Actualizar unidad de medida
        if (request.getUnidadMedida() != null) {
            UnidadMedida unidadMedida = unidadMedidaRepository.findById(request.getUnidadMedida())
                    .orElseThrow(() -> new RuntimeException("Unidad de medida no encontrada con ID: " + request.getUnidadMedida()));
            producto.setUnidadMedida(unidadMedida);
        }

        return productoRepository.save(producto);
    }
}