package dhbkhn.kien.doan2.ui.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import dhbkhn.kien.doan2.R;
import dhbkhn.kien.doan2.ui.base.BaseFragment;

/**
 * Created by kiend on 5/15/2017.
 */

public class AboutFragment extends BaseFragment implements AboutMvpView{

    public static final String TAG = "AboutFragment";

    @Inject
    AboutMvpPresenter<AboutMvpView> mPresenter;

    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this, view));

        setUp(view);

        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUp(View view) {
        view.setOnClickListener(v -> {

        });
    }

    @OnClick(R.id.nav_back_btn)
    void onNavBackClick(){
        getBaseActivity().onFragmentDetached(AboutFragment.class.getSimpleName());
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDettach();
        super.onDestroyView();
    }
}
