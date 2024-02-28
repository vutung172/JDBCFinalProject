package com.module3.service;

import java.util.List;

public interface GeneralService<T> {
    void listAll();
    void create();
    void update();
    void search(String any);
}
