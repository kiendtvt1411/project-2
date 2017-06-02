package dhbkhn.kien.doan2.ui.detail;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dhbkhn.kien.doan2.R;
import dhbkhn.kien.doan2.data.db.model.EspOne;
import dhbkhn.kien.doan2.data.db.model.EspTwo;
import dhbkhn.kien.doan2.ui.base.BaseFragment;
import dhbkhn.kien.doan2.utils.ScreenUtils;

/**
 * Created by kiend on 5/30/2017.
 */

public class DetailFragment extends BaseFragment implements DetailMvpView{

    public static String TAG = DetailFragment.class.getSimpleName();

    public static String ID_ROOM = "id_room";

    private int mIdRoom;

    @BindView(R.id.swipe_card_container)
    SwipePlaceHolderView mSwipeCardHolder;

    @Inject
    DetailMvpPresenter<DetailMvpView> mPresenter;

    public static DetailFragment newInstance(int idRoom){
        Bundle args = new Bundle();
        args.putInt(ID_ROOM, idRoom);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this, view));

        mPresenter.onAttach(this);

        setUp(view);

        return view;
    }

    private void setupCardContainerView(){

        int screenWidth = ScreenUtils.getScreenWidth(getBaseActivity());
        int screenHeight = ScreenUtils.getScreenHeight(getBaseActivity());

        mSwipeCardHolder.getBuilder()
                .setDisplayViewCount(3)
                .setHeightSwipeDistFactor(10)
                .setWidthSwipeDistFactor(5)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth((int) (0.90 * screenWidth))
                        .setViewHeight((int) (0.90 * screenHeight))
                        .setPaddingTop(50)
                        .setSwipeRotationAngle(10)
                        .setRelativeScale(0.01f)
                );

        mSwipeCardHolder.addItemRemoveListener(count -> {
            if (count == 0) {
                // reload the contents again after 1 sec delay
                new Handler(Looper.getMainLooper()).postDelayed(() ->
                        mPresenter.onCardExhausted(mIdRoom), 800);
            }
        });
    }

    @Override
    protected void setUp(View view) {
        mIdRoom = getArguments().getInt(ID_ROOM, 0);
        if(mIdRoom == 0) return;
        setupCardContainerView();
        mPresenter.onInitiateCardHolder(mIdRoom);
    }

    @OnClick(R.id.nav_back_btn)
    void onNavBackClick(){
        getBaseActivity().onFragmentDetached(DetailFragment.TAG);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDettach();
        super.onDestroyView();
    }

    @Override
    public void refreshHistoryRoomOne(List<EspOne> espOneList) {
        for (EspOne esp : espOneList) {
            if (esp != null) {
                mSwipeCardHolder.addView(new HistoryCardOne(esp));
            }
        }
    }

    @Override
    public void refreshHistoryRoomTwo(List<EspTwo> espTwoList) {
        for (EspTwo esp : espTwoList) {
            if (esp != null) {
                mSwipeCardHolder.addView(new HistoryCardTwo(esp));
            }
        }
    }

    @Override
    public void reloadHistoryRoomOne(List<EspOne> espOneList) {
        refreshHistoryRoomOne(espOneList);
        ScaleAnimation animation =
                new ScaleAnimation(
                        1.15f, 1, 1.15f, 1,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f
                );
        mSwipeCardHolder.setAnimation(animation);
        animation.setDuration(100);
        animation.start();
    }

    @Override
    public void reloadHistoryRoomTwo(List<EspTwo> espTwoList) {
        refreshHistoryRoomTwo(espTwoList);
        ScaleAnimation animation =
                new ScaleAnimation(
                        1.15f, 1, 1.15f, 1,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f
                );
        mSwipeCardHolder.setAnimation(animation);
        animation.setDuration(100);
        animation.start();
    }
}
