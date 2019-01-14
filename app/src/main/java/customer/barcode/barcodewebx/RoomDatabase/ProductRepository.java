package customer.barcode.barcodewebx.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.util.List;

import customer.barcode.barcodewebx.AsyncResult;

public class ProductRepository implements AsyncResult {

    private WordDao mWordDao;
    private LiveData<List<mytable>> mAllProd;
    private LiveData<List<historytable>> mAllhis;
    private MutableLiveData<Productltable> searchResults =
            new MutableLiveData<>();



    ProductRepository(Application application) {
        ProductRoomDatabase db = ProductRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllProd = mWordDao.getAllWords();
        mAllhis=mWordDao.getAllHis();
     //   mytable=mWordDao.getrowinfo(barcode);

    }

    LiveData<List<mytable>> getAllWords() {
        return mAllProd;
    }

    LiveData<List<historytable>> getAllHis() {
        return mAllhis;
    }

    @Override
    public void asyncFinished(Productltable results) {
        searchResults.setValue(results);

    }
    public MutableLiveData<Productltable> getSearchResults() {
        return searchResults;
    }
    public void insertProduct(Productltable newproduct) {
        new queryAsyncTask.insertAsyncTask(mWordDao).execute(newproduct);
    }
    public void findProduct(String name) {
        queryAsyncTask task = new queryAsyncTask(mWordDao);
        task.delegate = this;
        task.execute(name);
    }


    private static class queryAsyncTask extends
            AsyncTask<String, Void, Productltable> {

        private WordDao asyncTaskDao;
        private ProductRepository delegate = null;

        queryAsyncTask(WordDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Productltable doInBackground(final String... params) {
            return asyncTaskDao.findProduct(params[0]);
        }

        @Override
        protected void onPostExecute(Productltable result) {
            delegate.asyncFinished(result);
        }

        private static class insertAsyncTask extends AsyncTask<Productltable, Void, Void> {

            private WordDao asyncTaskDao;

            insertAsyncTask(WordDao dao) {
                asyncTaskDao = dao;
            }

            @Override
            protected Void doInBackground(final Productltable... params) {
                asyncTaskDao.insertProductforlist(params[0]);
                return null;
            }
        }
    }












        public void insert (mytable table) {
        new insertAsyncTask(mWordDao).execute(table);
    }




    public void deleterow(mytable mtable){new deleteit(mWordDao).execute(mtable);}

    public void deletallhis(){new deleteallhis(mWordDao).execute();}
    public void deletallpro(){new deleteallhis(mWordDao).execute();}
    public void deletallproducts(){new deleteallproducts(mWordDao).execute();}


    public void updaterow(long newitems ,long barcode){new updateit(mWordDao).execute( newitems , barcode);}

    //histrorytable
    public void insert (historytable hist) {
        new insertAsyncTaskhis(mWordDao).execute(hist);
    }




    private static class insertAsyncTaskhis extends AsyncTask<historytable, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTaskhis(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final historytable... params) {
            mAsyncTaskDao.inserthis(params[0]);
            return null;
        }
    }





    private static class insertAsyncTask extends AsyncTask<mytable, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final mytable... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }





    private static class deleteit extends AsyncTask<mytable, Void, Void> {

        private WordDao mAsyncTaskDao;

        deleteit(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(mytable... params) {
            mAsyncTaskDao.deleteit(params[0]);
            return null;
        }
    }
    private static class updateit extends AsyncTask<Long, Void, Void> {

        private WordDao mAsyncTaskDao;

        updateit(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Long... params) {
            mAsyncTaskDao.updateproduct(params[0],params[1]);
            return null;
        }
    }

    private static class deleteallhis extends AsyncTask<Long, Void, Void> {

        private WordDao mAsyncTaskDao;

        deleteallhis(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Long... params) {
            mAsyncTaskDao.deleteAllHis();
            return null;
        }
    }
    private static class deleteallproducts extends AsyncTask<Long, Void, Void> {

        private WordDao mAsyncTaskDao;

        deleteallproducts(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Long... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }




}
