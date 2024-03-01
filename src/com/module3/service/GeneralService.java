package com.module3.service;

import java.util.List;

public interface GeneralService<T> {
    List<T> listAll();
    T create();
    T update();
    List<T> search(String any);
}
