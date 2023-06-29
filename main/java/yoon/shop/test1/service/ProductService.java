package yoon.shop.test1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoon.shop.test1.domain.Members;
import yoon.shop.test1.domain.Product;
import yoon.shop.test1.dto.ProductDto;
import yoon.shop.test1.repository.MemberRepository;
import yoon.shop.test1.repository.ProductRepository;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public List<Product> read(){
        List<Product> list  = productRepository.findAll();
        return list;
    }

    public ProductDto read(String idx) throws  Exception{
        Long id = Long.parseLong(idx);

        Product product = productRepository.findProductByIdx(id);
        if(product==null)
            return new ProductDto("None",null,0);   //No Such Product

        return new ProductDto(product.getName(), product.getCategory(), product.getPrice());
    }

    public ProductDto read(String idx, Principal principal) throws  Exception{
        Long id = Long.parseLong(idx);

        Product product = productRepository.findProductByIdx(id);
        Members member = memberRepository.findMembersByEmail(principal.getName());

        if(product == null || member == null)
            return new ProductDto("None",null,0);   //No Such Product
        if(product.getSeller() != member)
            return new ProductDto("Not",null,0);    //Not Same Seller

        return new ProductDto(product.getName(), product.getCategory(), product.getPrice());
    }

    public ProductDto join(Principal principal, ProductDto dto) throws  Exception{

        Members member = memberRepository.findMembersByEmail(principal.getName());

        if(member==null)
            return new ProductDto("None", null ,0);   //Member not Found
        if(dto.getName()==null||dto.getCategory()==null||dto.getPrice()<=0)
            return new ProductDto("Not",null,0);       //Product Data not Valid
        Product product = Product.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .seller(member)
                .build();
        productRepository.save(product);
        return new ProductDto(product.getName(), product.getCategory(), product.getPrice());
    }

    public ProductDto update(String idx, ProductDto dto) throws  Exception{
        Long id = Long.parseLong(idx);
        Product product = productRepository.findProductByIdx(id);
        if(product == null)
            return new ProductDto("None",null,0);

        if(dto.getName() == null || dto.getCategory() == null || dto.getPrice()<=0)
            return new ProductDto("Not", null, 0);
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());

        productRepository.save(product);

        return new ProductDto(dto.getName(), dto.getCategory(), dto.getPrice());
    }

    public ProductDto delete(String idx, Principal principal)throws Exception{

        Long id = Long.parseLong(idx);
        Members member = memberRepository.findMembersByEmail(principal.getName());
        Product product = productRepository.findProductByIdx(id);
        if(member == null || product == null)
            return new ProductDto("None", null, 0);
        if(product.getSeller() != member)
            return new ProductDto("Not", null, 0);

        ProductDto productDto = new ProductDto(product.getName(), product.getCategory(), product.getPrice());
        productRepository.delete(product);

        return productDto;
    }
}
