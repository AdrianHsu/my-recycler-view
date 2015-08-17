/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.example.android.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.example.android.common.activities.SampleActivityBase;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";
    public static PullRefreshLayout layout;
    boolean init = false;

    public static ArrayList<Card> cards = new ArrayList<Card>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      if (savedInstanceState == null) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();
      }

      layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
      layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
          layout.postDelayed(new Runnable() {
            @Override
            public void run() {
//              updateAdapter(cards);
              layout.setRefreshing(false);
              RecyclerViewFragment.mAdapter.notifyDataSetChanged();
            }
          }, 2000);
        }
      });
      layout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

      if(init == false) {
        initCard();
        init = true;
      }
    }
    public void initCard() {
      for(int i = 0; i < 60; i ++) {
        //Create a Card
        Card card = new Card(this);
        card.setTitle("this is card #" + i);
        //Create a CardHeader
        CardHeader header = new CardHeader(this);
        header.setTitle("Adrian's card");
        header.setButtonExpandVisible(true);

        //Add Header to card
        card.addCardHeader(header);

        //This provides a simple (and useless) expand area
        CardExpand expand = new CardExpand(this);
        //Set inner title in Expand Area
        expand.setTitle("Expand Area test");
        card.addCardExpand(expand);
        card.setExpanded(false);

        CardThumbnail thumb = new CardThumbnail(this);
        thumb.setDrawableResource(R.drawable.adrian);
        card.addCardThumbnail(thumb);

        card.setClickable(true);
        //Add ClickListener
        card.setOnClickListener(new Card.OnCardClickListener() {

          @Override
          public void onClick(Card card, View view) {
//            Toast.makeText(MainActivity.this, "Click Listener card=" + card.getTitle(), Toast
//              .LENGTH_SHORT)
//              .show();
          }
        });
        cards.add(card);
      }
    }
    /**
     * Update the adapter
     */
    public void updateAdapter(ArrayList<Card> cards) {
      if (cards != null) {
//        RecyclerViewFragment.mAdapter.addAll(cards);
      }
    }

}
