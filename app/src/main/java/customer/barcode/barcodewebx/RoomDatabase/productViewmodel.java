package customer.barcode.barcodewebx.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;


public class productViewmodel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<List<mytable>> mAllpro;
    private LiveData<List<historytable>> mallhis;
    private MutableLiveData<Productltable> searchResults;



    public productViewmodel (Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        mAllpro = mRepository.getAllWords();
        mallhis=mRepository.getAllHis();
        searchResults = mRepository.getSearchResults();

    }

   public LiveData<List<mytable>> getAllWords() { return mAllpro; }

    public MutableLiveData <Productltable> getSearchResults() {
        return searchResults;
    }

    public void insertProductforlist(Productltable product) {
       mRepository.insertProduct(product);
    }

    public void findProduct(String barcode) {
        mRepository.findProduct(barcode);
    }





    public void insert(mytable word) { mRepository.insert(word); }



    public void delterow(mytable num){mRepository.deleterow(num);}

    public void updateproduct(long newitems ,long barcode){mRepository.updaterow( newitems , barcode);}

    ///history section
    public LiveData<List<historytable>> getAllhistory() { return mallhis; }

    public void inserthis(historytable hist) { mRepository.insert(hist); }
    public void deleteallhist(){mRepository.deletallhis();}
    public void deleteallproduct(){mRepository.deletallproducts();}


    //insert in productdetails list


}
