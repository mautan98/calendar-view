package com.namviet.vtvtravel.di;

import com.namviet.vtvtravel.view.f3.search.view.SearchSuggestionForSpecificContentFragment;
import com.namviet.vtvtravel.view.f3.search.view.SearchSuggestionFragment;
import com.namviet.vtvtravel.view.fragment.f2account.RulesFragment;

import dagger.Component;

@Component
public interface ViewModelComponent {
    void inject(RulesFragment rulesFragment);
    void inject(SearchSuggestionFragment searchSuggestionFragment);
    void inject(SearchSuggestionForSpecificContentFragment searchSuggestionFragment);

}
