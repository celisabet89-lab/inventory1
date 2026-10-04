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
public class CreateProduct {

    private final ProductRepository productRepository;
    private final CategoriaRepository categoriaRepository;
    private final UnidadMedidaRepository unidadMedidaRepository;

    @Transactional
    public Product execute(ProductRequest request) {
        // Buscar y validar categoría
        Categoria categoria = null;
        if (request.getCategoria() != null) {
            categoria = categoriaRepository.findById(request.getCategoria())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + request.getCategoria()));
        }

        // Buscar y validar unidad de medida
        UnidadMedida unidadMedida = null;
        if (request.getUnidadMedida() != null) {
            unidadMedida = unidadMedidaRepository.findById(request.getUnidadMedida())
                    .orElseThrow(() -> new RuntimeException("Unidad de medida no encontrada con ID: " + request.getUnidadMedida()));
        }

        // Crear product
        Product product = new Product();
        product.setArticulo(request.getArticulo());
        product.setDescripcion(request.getDescripcion());
        product.setUnidad(request.getUnidad());
        product.setFoto(request.getFoto());
        product.setPrecio(request.getPrecio());
        product.setCodigoBarras(request.getCodigoBarras());
        product.setCategoria(categoria);
        product.setUnidadMedida(unidadMedida);

        return productRepository.save(product);
    }
}