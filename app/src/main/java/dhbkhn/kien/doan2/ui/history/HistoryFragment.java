package dhbkhn.kien.doan2.ui.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbkhn.kien.doan2.R;
import dhbkhn.kien.doan2.ui.base.BaseFragment;
import dhbkhn.kien.doan2.ui.detail.DetailFragment;

/**
 * Created by kiend on 5/16/2017.
 */

public class HistoryFragment extends BaseFragment implements HistoryMvpView {

    private static final String TAG = HistoryFragment.class.getSimpleName();

    @BindView(R.id.tvMoreOne)
    TextView mTvMoreOne;

    @BindView(R.id.tvMoreTwo)
    TextView mTvMoreTwo;

    @Inject
    HistoryMvpPresenter<HistoryMvpView> mPresenter;

    public static HistoryFragment newInstance(){
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this, view));

        mPresenter.onAttach(this);

        setUp(view);

        return view;
    }

    @Override
    protected void setUp(View view) {
        mTvMoreOne.setOnClickListener(view1 -> mPresenter.onClickDetailRoomOne());
        mTvMoreTwo.setOnClickListener(view1 -> mPresenter.onClickDetailRoomTwo());
    }

    @Override
    public void openDetailFragment(int idRoom) {
        getBaseActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.cl_root_view, DetailFragment.newInstance(idRoom), DetailFragment.TAG)
                .commit();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDettach();
        super.onDestroyView();
    }
}
