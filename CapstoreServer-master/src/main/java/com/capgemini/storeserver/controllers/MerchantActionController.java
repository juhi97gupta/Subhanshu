package com.capgemini.storeserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.capgemini.storeserver.beans.Merchant;
import com.capgemini.storeserver.beans.Product;
import com.capgemini.storeserver.exceptions.MerchantNotFoundException;
import com.capgemini.storeserver.exceptions.ProductNotFoundException;
import com.capgemini.storeserver.services.MerchantServices;
import com.capgemini.storeserver.services.MerchantServicesImpl;

@RestController
@RequestMapping("/products")
public class MerchantActionController {

	
	private MerchantServices merchantService=new MerchantServicesImpl();


	@PostMapping(value = "/addProduct", consumes= {"application/json"})
	public int addProduct(@RequestParam("merchantId") int merchantId, @RequestBody Product product) throws MerchantNotFoundException {

		Merchant merchant;
		int id = 0;
		//product.setMerchant(merchant);
		try {
			merchant=merchantService.getMerchantById(merchantId);
			
			id = merchantService.addProduct(product);
			
		} catch (ProductNotFoundException e) {

			return id;
		}
		 catch (MerchantNotFoundException e) {

				return id;
			}
		return id;
	}

	@DeleteMapping(value = "/removeProduct")
	public void removeProduct(@RequestParam("merchantId") int merchantId,@PathVariable int productId) {
		Merchant merchant;
		try {
		merchant=merchantService.getMerchantById(merchantId);
		
		merchantService.removeProduct(productId);
		}
		catch (ProductNotFoundException e) {

			
		}
		 catch (MerchantNotFoundException e) {

				
			}
	}

	@PutMapping(value = "/updateProduct" ,consumes= {"application/json"})
	public Product updateProduct(@RequestParam("merchantId") int merchantId,@RequestBody Product product) throws ProductNotFoundException, MerchantNotFoundException {
		Merchant merchant;
		try {
			merchant=merchantService.getMerchantById(merchantId);
			merchantService.updateProduct(product);
		} catch (ProductNotFoundException e) {

			return null;
		}
		 catch (MerchantNotFoundException e) {

				
			}
		return product;
	}


	@GetMapping(value = "/getAllProducts")
	public @ResponseBody List<Product> getAllProduct(@RequestParam("merchantId") int merchantId) {

		List<Product> product = merchantService.getAllProducts(merchantId);
		
		return product;
	}
	/*
	@RequestMapping(value = "/getProductDetails")
	public Product getProductDetails(@RequestParam("productId") int productId) {

		Product product = merchantService.getProductDetails(productId);
		return product;
	}

 * 
 * @RequestMapping(value = "/merchantSignIn", method = RequestMethod.POST)
	public void addMerchant(@RequestBody Merchant merchant) {

		merchantService.registerMerchant(merchant);
	}


	@RequestMapping(value = "myProfilesuccess")
	public Merchant myProfile(int merchantId) {
		Merchant merchant = merchantService.findMerchantId(merchantId);
		return merchant;
	}
	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	public void changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("password") String password, @RequestParam("merchantId") int merchantId) {
		Merchant merchant = merchantService.findMerchantId(merchantId);
		if (Base64Coder.decodeString(merchant.getPassword()).compareTo(oldPassword) != 0) {
			merchantService.changePassword(merchant, password);
		}
	}

	@RequestMapping(value = "forgotPassword", method = RequestMethod.GET)
	public String forgotPassword(@RequestParam("email") String email,
			@RequestParam("securityAnswer") String securityAnswer) {
		try {

			Merchant merchant = merchantService.getMerchant(email);
			
			if (merchant.getSecurityAnswer().compareTo(securityAnswer) == 0) {
				return merchant.getPassword();
			}
		} catch (MerchantNotFoundException e) {

			return null;

		}
		return "Password not found";
	}*/
}
