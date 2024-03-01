package tw.eeit175groupone.finalproject.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.eeit175groupone.finalproject.dao.CommentsRepository;
import tw.eeit175groupone.finalproject.dao.ProductRepository;

import tw.eeit175groupone.finalproject.dto.*;

import tw.eeit175groupone.finalproject.service.DashboardProductService;


import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/dashboard")

public class DashboardProductController {
    @Autowired
    private DashboardProductService dashboardProductService;
    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private ProductRepository productRepository;


    //    接收請求後丟出某個商品封面照片給前端管理者
    @PostMapping("/productimage/{productId}")
    public ResponseEntity<?> findAllProduct(@PathVariable Integer productId){
        byte[] productImage= dashboardProductService.findCoverImageById(productId);
        if(productImage!=null){
            return ResponseEntity.ok().body(productImage);
        }
        return ResponseEntity.notFound().build();

    }
    //接收大量的商品id請求後，丟出商品封面照片給前端管理者
    @PostMapping("/productimage/find")
    public ResponseEntity<?> findAllProductImage(@RequestBody List<Integer> productIds){
        if(!productIds.isEmpty()&&productIds!=null){
            Map<Integer, String> allCoverImageByIdIn= dashboardProductService.findAllCoverImageByIdIn(productIds);
            if(allCoverImageByIdIn!=null){
                return ResponseEntity.ok().body(allCoverImageByIdIn);
            }
        }
        return ResponseEntity.notFound().build();
    }
    

    //更新商品狀態用
    @GetMapping("/product/update")
    public ResponseEntity<?> modityDiscountProduct(){
        dashboardProductService.modityDiscountProduct();
        return ResponseEntity.ok().body("success");
    }


    //接收請求後丟出全部的商品資訊給前端管理者
    @PostMapping("/product/find")
    public ResponseEntity<?> findProduct(@RequestBody DashboardProduct request){
        System.err.println("pfind="+request);
    
        if(request!=null){
            Map<String,Object> allProduct= dashboardProductService.findAllProduct(request);
            if(allProduct!=null ){
                if(allProduct.isEmpty()){
                    return ResponseEntity.ok().body("nodata");
                }
                return ResponseEntity.ok().body(allProduct);
            }
        }
        return ResponseEntity.notFound().build();

    }

    //1.接收請求，並接收資料
    //2.更改商品資料
    @PutMapping("/product")
    public ResponseEntity<?> modifyProduct(@RequestBody CompleteProductInfo requset){
        System.out.println("in");
        if(requset!=null){
            CompleteProductInfo newProductInfo= dashboardProductService.modifyProduct(requset);
            if(newProductInfo!=null){
                return ResponseEntity.ok().body(newProductInfo);
            }
        }
        return ResponseEntity.notFound().build();
    }

    //1.接收請求，並接收資料
    //2.批次更改商品資料
    @PutMapping("/product/all")
    public ResponseEntity<?> modifyAllProduct(@RequestBody List<CompleteProductInfo> request){
        if(request!=null&&!request.isEmpty()){
            List<CompleteProductInfo> results= dashboardProductService.modifyAllProduct(request);
            return ResponseEntity.ok().body(results);
            }
        
        return ResponseEntity.notFound().build();
    }


    //接收請求後丟出全部的商品資訊給前端管理者
    @GetMapping("/product/count")
    public ResponseEntity<?> countProduct(){
        Integer countProduct= dashboardProductService.countProduct();
        if(countProduct!=null){
                return ResponseEntity.ok().body(countProduct);
            }
        
        return ResponseEntity.notFound().build();
    }
    
    

    //1.接收請求，並接收資料
    //2.更改商品資料
//    @PutMapping("/product")
//    public ResponseEntity<?> modifyProduct(@RequestBody DashboardProduct requset){
//        System.out.println(123);
//        if(requset!=null){
//            System.out.println(456);
//            DashboardProduct updatedproduct=dashboardService.modifyProduct(requset);
//            if(updatedproduct!=null){
//                return ResponseEntity.ok().body(requset);
//            }
//        }
//        return ResponseEntity.noContent().build();
//    }
//    
    //接收到請求回傳商品類別
    @GetMapping("/product/producttype")
    public ResponseEntity<?> findProductType(){
        List<String> productType= dashboardProductService.findProductType();
        if(productType!=null && !productType.isEmpty()){
            return ResponseEntity.ok().body(productType);
        }
        return null;
    }
    //接收到請求回傳商品子類別
    @PostMapping("/product/subtype")
    public ResponseEntity<?> findProductType(@RequestBody String productType){
        System.out.println(productType);
        if(productType!=null){
            JSONObject json=new JSONObject(productType);

            List<String> subtype= dashboardProductService.findSubtype(json);
            if(subtype!=null && !subtype.isEmpty()){
                System.out.println(subtype);
                return ResponseEntity.ok().body(subtype);
            }
        }
        return null;
    }
    //接收到請求回傳商品名稱
    @PostMapping("/product/productname")
    public ResponseEntity<?> findProductName(@RequestBody String productSubtype){
        System.out.println(productSubtype);
        if(productSubtype!=null){
            JSONObject json=new JSONObject(productSubtype);

            List<String> productName= dashboardProductService.findProductName(json);
            if(productName!=null && !productName.isEmpty()){
                System.out.println(productName);
                return ResponseEntity.ok().body(productName);
            }
        }
        return null;
    }

}
