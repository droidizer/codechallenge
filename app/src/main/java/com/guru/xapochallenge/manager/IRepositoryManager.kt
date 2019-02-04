package com.guru.xapochallenge.manager

import com.guru.xapochallenge.api.model.Owner
import com.guru.xapochallenge.api.model.Repository
import com.guru.xapochallenge.api.model.RepositoryDetails
import io.reactivex.Observable

interface IRepositoryManager {

    fun searchRepositories(query: String, page: Int = 1, pageSize: Int = 25): Observable<List<Repository>>

    fun getRepositoryDetails(owner: String, repo: String): Observable<RepositoryDetails>

    fun getSubscribers(owner: String, repo: String, page: Int = 1, pageSize: Int = 25): Observable<List<Owner>>
}