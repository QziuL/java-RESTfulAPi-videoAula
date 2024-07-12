package br.qziul.video_aula.controllers;

import br.qziul.video_aula.dtos.ProductRecordDTO;
import br.qziul.video_aula.models.ProductModel;
import br.qziul.video_aula.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 *  Classe RestController para Produtos com endpoint "/products".
 */

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    // Anotação @Valid para ativar as validações definidas no ProductRecordDTO
    @PostMapping
    public ResponseEntity<ProductModel> save(@RequestBody @Valid ProductRecordDTO productRecordDTO) {
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDTO, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAll() {
        List<ProductModel> productList = productRepository.findAll();
        if(!productList.isEmpty())
            // Adicionar hipermídia (Hateoas)
            productList.forEach(product -> product.add(linkTo(methodOn(ProductController.class)
                    .getOneById(product.getIdProduct())).withSelfRel()));

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneById(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if(product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");

        // Adicionar hipermídia (Hateoas)
        product.get().add(linkTo(methodOn(ProductController.class).getAll()).withRel("Products List"));
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateById(@PathVariable(value = "id") UUID id,
                                             @RequestBody @Valid ProductRecordDTO productRecordDTO) {
        Optional<ProductModel> product = productRepository.findById(id);
        if(product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");

        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDTO, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> product = productRepository.findById(id);
        if(product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");

        productRepository.deleteById(product.get().getIdProduct());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }
}
