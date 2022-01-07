package com.example.Repository.PagingRepo;

import java.util.stream.Stream;

public interface PageCore<E>{
    PagebleCore getCurrentPage();

    PagebleCore getNextPage();

    Stream<E> getPageContent();
}
