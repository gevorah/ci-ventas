package co.edu.icesi.dev.uccareapp.transport.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.icesi.dev.uccareapp.transport.model.prchasing.Purchaseorderdetail;
import co.edu.icesi.dev.uccareapp.transport.model.prchasing.PurchaseorderdetailPK;
import co.edu.icesi.dev.uccareapp.transport.repository.PurchaseorderdetailRepository;
import co.edu.icesi.dev.uccareapp.transport.repository.PurchaseorderheaderRepository;

@Service
public class PurchaseorderdetailServiceImp implements PurchaseorderdetailService {

    @Autowired
    private PurchaseorderdetailRepository purchaseorderdetailrepository;

    @Autowired
    private PurchaseorderheaderRepository purchaseorderheaderrepository;

    @Override
    public boolean savePurchaseorderdetail(Purchaseorderdetail purchaseorderdetail)
            throws NullPointerException, IllegalArgumentException {
        if (purchaseorderdetail == null || purchaseorderdetailrepository
                .existsById(purchaseorderdetail.getId()))
            throw new NullPointerException("Purchaseorderdetail is null or already exists");

        if (!purchaseorderheaderrepository
                .existsById(purchaseorderdetail.getId().getPurchaseorderid()))
            throw new NullPointerException("Purchaseorderheader doesn't exist");
        if (purchaseorderdetail.getOrderqty() < 1)
            throw new IllegalArgumentException("orderqty must be greater than 0");
        if (purchaseorderdetail.getUnitprice().signum() != 1)
            throw new IllegalArgumentException("unitprice must be greater than 0");

        purchaseorderdetailrepository.save(purchaseorderdetail);

        return true;
    }

    @Override
    public boolean editPurchaseorderdetail(Purchaseorderdetail purchaseorderdetail)
            throws NullPointerException, IllegalArgumentException {
        if (purchaseorderdetail == null ||
                !purchaseorderdetailrepository.existsById(purchaseorderdetail.getId()))
            throw new NullPointerException("Purchaseorderdetail is null or doesn't exist");

        if (!purchaseorderheaderrepository
                .existsById(purchaseorderdetail.getPurchaseorderheader().getPurchaseorderid()))
            throw new NullPointerException("Purchaseorderheader doesn't exist");
        if (purchaseorderdetail.getOrderqty() < 1)
            throw new IllegalArgumentException("orderqty must be greater than 0");
        if (purchaseorderdetail.getUnitprice().signum() != 1)
            throw new IllegalArgumentException("unitprice must be greater than 0");

        Purchaseorderdetail editpurchaseorderdetail = purchaseorderdetailrepository
                .findById(purchaseorderdetail.getId()).get();
        editpurchaseorderdetail.setDuedate(purchaseorderdetail.getDuedate());
        editpurchaseorderdetail.setModifieddate(purchaseorderdetail.getModifieddate());
        editpurchaseorderdetail.setOrderqty(purchaseorderdetail.getOrderqty());
        editpurchaseorderdetail.setProductid(purchaseorderdetail.getProductid());
        //editpurchaseorderdetail.setPurchaseorderheader(purchaseorderheaderrepository
        //        .findById(purchaseorderdetail.getPurchaseorderheader().getPurchaseorderid()).get());
        editpurchaseorderdetail.setReceivedqty(purchaseorderdetail.getReceivedqty());
        editpurchaseorderdetail.setRejectedqty(purchaseorderdetail.getReceivedqty());
        editpurchaseorderdetail.setUnitprice(purchaseorderdetail.getUnitprice());

        purchaseorderdetailrepository.save(editpurchaseorderdetail);

        return true;
    }

    @Override
    public Optional<Purchaseorderdetail> findById(PurchaseorderdetailPK id) {
        return purchaseorderdetailrepository.findById(id);
    }

    @Override
    public Iterable<Purchaseorderdetail> findAll() {
        return purchaseorderdetailrepository.findAll().iterator().hasNext()?
                purchaseorderdetailrepository.findAll() : null;
    }

    @Override
    public void delete(Purchaseorderdetail purchaseorderdetail) {
        purchaseorderdetailrepository.delete(purchaseorderdetail);
    }

}
