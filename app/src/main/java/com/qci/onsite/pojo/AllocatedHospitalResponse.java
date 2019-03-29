package com.qci.onsite.pojo;

import java.util.ArrayList;

/**
 * Created by Ankit on 15-02-2019.
 */

public class AllocatedHospitalResponse {

    private int total;
    private int rowCount;
    private int current;
    private ArrayList<AllocatedHospitalListPojo>rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public ArrayList<AllocatedHospitalListPojo> getRows() {
        return rows;
    }

    public void setRows(ArrayList<AllocatedHospitalListPojo> rows) {
        this.rows = rows;
    }


}
