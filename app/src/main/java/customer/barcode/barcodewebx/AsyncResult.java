package customer.barcode.barcodewebx;

import java.util.List;

import customer.barcode.barcodewebx.RoomDatabase.Productltable;

public interface AsyncResult {
    void asyncFinished(Productltable results);
}