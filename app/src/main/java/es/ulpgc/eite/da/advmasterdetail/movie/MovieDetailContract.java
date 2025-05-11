package es.ulpgc.eite.da.advmasterdetail.movie;

import java.lang.ref.WeakReference;

interface MovieDetailContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void displayProductDetailData(MovieDetailViewModel viewModel);
  }

  interface Presenter {
    void injectView(WeakReference<View> view);
    void injectModel(Model model);

    void fetchProductDetailData();

    void onCreateCalled();

    void onRecreateCalled();

      void onPauseCalled();
  }

  interface Model {

  }

}