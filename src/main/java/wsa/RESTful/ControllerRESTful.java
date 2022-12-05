/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wsa.RESTful;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ControllerRESTful {
    private static final Map<String, Product> productRepo = new HashMap<>();
    static {
        Product honey = new Product();
        honey.setId("1");
        honey.setName("Honey");
        productRepo.put(honey.getId(), honey);
        
        Product almond = new Product();
        almond.setId("2");
        almond.setName("Almond");
        productRepo.put(almond.getId(), almond);
    }
    
    @RequestMapping(value = "products/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id")String id){
        {
        productRepo.remove(id);
        return new ResponseEntity<>("Product is deleted successfully",HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable("id")String id, @RequestBody Product product){
        //menambahkan statement if
        if(!productRepo.containsKey(id))
        {
            return new ResponseEntity<>("No ID", HttpStatus.NOT_FOUND);//jika id tidak ada, maka tidak akan bisa diupdate
        }
        //menambahkan statement else
        else
        {
        productRepo.remove(id);
        product.setId(id);
        productRepo.put(id,product);
        return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        //menambahkan statement if
        if(!productRepo.containsKey(product.getId()))
        {
            return new ResponseEntity<>("The product can't be added because it already exists", HttpStatus.NOT_ACCEPTABLE);
        }//apabila id nya sama maka tidak akan bisa menambahkan
        //menambahkan statement else
        else
        {
            productRepo.put(product.getId(), product);
            return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
        }
    }
    
    @RequestMapping(value = "/products")
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
    }
}