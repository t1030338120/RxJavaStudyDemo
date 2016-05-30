package com.app.rxjava.loader_mvp;

public interface PresenterFactory<T extends Presenter> {
      T create();
  } 