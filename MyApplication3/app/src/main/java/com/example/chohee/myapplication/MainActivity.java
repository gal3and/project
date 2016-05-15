package com.example.chohee.myapplication;


import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.gms.common.api.GoogleApiClient;
import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements AccountItemDialog.OnsetItem{


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private GoogleApiClient client;
    static public AccountItemDto DATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mSectionsPagerAdapter.notifyDataSetChanged();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }  이메일 버튼 누를시 버튼
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void returnResult(AccountItemDto data) {
        this.DATA=data;
        Account_bnt_active active_fragment=(Account_bnt_active)getSupportFragmentManager().findFragmentById(R.id.account_btn_fragment);
        if(active_fragment!=null){
            active_fragment.inputItem(data);
        }else{
            Account_bnt_active newfragment=new Account_bnt_active();
            Bundle b=new Bundle();
            b.putString("date",data.getBuy_date());
            b.putString("item_name",data.getItem_name());
            b.putInt("price",data.getItem_price());
            newfragment.setArguments(b);

            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_fragment,newfragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
             return rootView;
        }    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public ArrayList<Fragment> fragments=new ArrayList<Fragment>();
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            if(position!=getItemPosition(object)){
                FragmentManager fragmentManager=((Fragment) object).getFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                transaction.remove((Fragment)object);
                transaction.commit();
            }
         }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
            super.restoreState(state, loader);
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            if(fragments.contains(object)) {
                return fragments.indexOf(object);
            }
            else return POSITION_NONE;
        }

        public Fragment getItem(int position) {

            Fragment f=new Fragment();
            Context c;

            switch (position) {
                case 0:
                    f=new nalActivity();
                    break;
                case 1:
                    f=new MapActivity();
                    break;
                case 2:
                    f=new AccountActivity();
                    break;
                case 3:
                    f=new TipActivity();
                    break;
                case 4:
                    f=new SettingActivity();
                    break;

            }
            return f;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "날씨";
                case 1:
                    return "지도";
                case 2:
                    return "가계부";
                case 3:
                    return "신고서";
                case 4:
                    return "설정";
            }
            return null;
        }
    }
}
