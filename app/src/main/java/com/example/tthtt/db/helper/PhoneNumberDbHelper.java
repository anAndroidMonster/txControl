package com.example.tthtt.db.helper;

import com.example.tthtt.db.AppDbHelper;
import com.example.tthtt.model.PhoneNumberModel;
import com.example.tthtt.model.PhoneNumberModelDao;

import java.util.List;

/**
 * Created by book4 on 2018/3/16.
 */

public class PhoneNumberDbHelper {

    private static PhoneNumberDbHelper mInstance;
    private PhoneNumberModelDao mDao;

    public static PhoneNumberDbHelper getInstance(){
        if(mInstance == null){
            synchronized (PhoneNumberDbHelper.class){
                if(mInstance == null){
                    mInstance = new PhoneNumberDbHelper();
                }
            }
        }
        return mInstance;
    }

    private PhoneNumberDbHelper(){
        mDao = AppDbHelper.getInstance().getDaoSession().getPhoneNumberModelDao();
    }

    public void put(List<PhoneNumberModel> dataList){
        if(dataList == null || dataList.size() <= 0) return;
        String city = dataList.get(0).getCity();
        int company = dataList.get(0).getCompany();
        List<PhoneNumberModel> oldList = mDao.queryBuilder().where(PhoneNumberModelDao.Properties.City.eq(city), PhoneNumberModelDao.Properties.Company.eq(company)).list();
        if(oldList != null && oldList.size() > 0) {
            mDao.deleteInTx(oldList);
        }
        mDao.insertInTx(dataList);
    }

    public void put(PhoneNumberModel data){
        if(data == null ) return;
        mDao.insertOrReplace(data);
    }

    public long getCount(String city, int type){
        return mDao.queryBuilder().where(PhoneNumberModelDao.Properties.City.eq(city), PhoneNumberModelDao.Properties.Company.eq(type)).count();
    }

    public long getCount(String city){
        return mDao.queryBuilder().where(PhoneNumberModelDao.Properties.City.eq(city)).count();
    }

    public PhoneNumberModel getRecord(String city, int index){
        return mDao.queryBuilder().where(PhoneNumberModelDao.Properties.City.eq(city)).offset(index).limit(1).unique();
    }

    public long getCount(){
        return mDao.count();
    }

    public PhoneNumberModel getMaxOne(){
        return mDao.queryBuilder().orderDesc(PhoneNumberModelDao.Properties.Id).limit(1).unique();
    }

    public PhoneNumberModel getMinOne(){
        return mDao.queryBuilder().orderAsc(PhoneNumberModelDao.Properties.Id).limit(1).unique();
    }

}
