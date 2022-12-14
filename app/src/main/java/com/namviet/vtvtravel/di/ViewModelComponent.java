package com.namviet.vtvtravel.di;

import com.namviet.vtvtravel.view.f3.deal.view.dealrule.DealRuleFragment;
import com.namviet.vtvtravel.view.f3.search.view.SearchSuggestionForSpecificContentFragment;
import com.namviet.vtvtravel.view.f3.search.view.SearchSuggestionFragment;
import com.namviet.vtvtravel.di.module.StorageModule;
import com.namviet.vtvtravel.view.f3.smalllocation.view.fragment.SearchSuggestionSmallLocationFragment;
import com.namviet.vtvtravel.view.f3.smalllocation.view.fragment.SmallLocationMainPageFragment;
import com.namviet.vtvtravel.view.fragment.f2account.RulesFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = StorageModule.class)
public interface ViewModelComponent {
    void inject(RulesFragment rulesFragment);
    void inject(SearchSuggestionFragment searchSuggestionFragment);
    void inject(SmallLocationMainPageFragment smallLocationMainPageFragment);
    void inject(SearchSuggestionForSpecificContentFragment searchSuggestionFragment);
    void inject(SearchSuggestionSmallLocationFragment searchSuggestionFragment);
    void inject(DealRuleFragment searchSuggestionFragment);
}
