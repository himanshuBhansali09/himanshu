package com.ttn.ecommerceApplication.ecommerceApplication.repository;

import com.ttn.ecommerceApplication.ecommerceApplication.entities.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart,Long> {
    Cart findByCustomerId(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from cart where product_variation_id=:product_variation_id",nativeQuery = true)
    void deleteByProductVariationId(@Param("product_variation_id") Long product_variation_id);

    @Modifying
    @Transactional
    @Query(value = "delete from cart where customer_id=:customer_id",nativeQuery = true)
    void emptyUserCart(@Param("customer_id") Long customer_id);

    @Query(value = "select * from cart where customer_id=:customer_id",nativeQuery = true)
    List<Cart> getCarts(@Param("customer_id") Long customer_id);

    @Query(value = "select product_variation.id,info_json,price,product_id,productname from product_variation join " +
            "product on product_variation.product_id=product.id and product_variation.id in" +
            " (select product_variation_id from cart where customer_id=:customer_id)",nativeQuery = true)
    List<Object[]> viewCart(@Param("customer_id") Long customer_id);

    @Query(value = "select product_variation.id from product_variation join " +
            "product on product_variation.product_id=product.id and product_variation.id in" +
            " (select product_variation_id from cart where customer_id=:customer_id)",nativeQuery = true)
    List<Long> getProductVariationsFromCart(@Param("customer_id") Long customer_id);

    @Query(value = "SELECT distinct\n" +
            "    okhb.contract_number \"Contract_Number\",\n" +
            "    okhb.contract_number_modifier \"Contract_Modifier\",\n" +
            "    hp.party_number \"Party_number\",\n" +
            "    hou1.name \"name\",\n" +
            "    okhb.sts_code \"Sts_Code\",\n" +
            "    okhb.scs_code \"scs_code\",\n" +
            "    okhb.date_renewed \"Date_Renewed\",\n" +
            "    okhb.start_date \"start_date\",\n" +
            "    okhb.end_date \"end_date\"\n" +
            "FROM\n" +
            "    hr_organization_units hou1,\n" +
            "    hz_parties hp,\n" +
            "    okc_k_party_roles_b okpr,\n" +
            "    okc_k_headers_all_b okhb,\n" +
            "    okc_k_lines_b oklb,\n" +
            "    okc_k_items oki,\n" +
            "    csi_item_instances cii\n" +
            "WHERE\n" +
            "    okpr.dnz_chr_id = okhb.id\n" +
            "    AND okpr.object1_id1 = hp.party_id\n" +
            "    AND okhb.authoring_org_id = hou1.organization_id\n" +
            "    AND hp.party_number = nvl(p_party_number,hp.party_number)\n" +
            "    and okpr.jtot_object1_code ='OKX_PARTY'\n" +
            "    AND okhb.contract_number = nvl(p_contract_number,okhb.contract_number)\n" +
            "    AND okhb.contract_number_modifier = nvl(p_contract_modifier,okhb.contract_number_modifier)\n" +
            "    AND oklb.dnz_chr_id = okhb.id\n" +
            "    AND oklb.lse_id in (9,18)\n" +
            "    AND oklb.id = oki.cle_id\n" +
            "    AND oki.jtot_object1_code = 'OKX_CUSTPROD'\n" +
            "    AND oki.object1_id1 = cii.instance_number\n" +
            "    AND cii.instance_id = nvl(p_instance_id,cii.instance_id)\n" +
            "    AND nvl(cii.serial_number,'E-E') = nvl(nvl(p_serial_number,cii.serial_number),'E-E');\n",nativeQuery = true)
    List<Object[]> sadf();

}
