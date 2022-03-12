package br.com.calculaflex.data.di

import br.com.calculaflex.data.remote.datasource.*
import br.com.calculaflex.data.repository.AppRepositoryImpl
import br.com.calculaflex.data.repository.CarRepositoryImpl
import br.com.calculaflex.data.repository.UserRepositoryImpl
import br.com.calculaflex.domain.repository.AppRepository
import br.com.calculaflex.domain.repository.CarRepository
import br.com.calculaflex.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModules = module {

    factory<AppRemoteDataSource> {
        AppRemoteFirebaseDataSourceImpl()
    }

    factory<CarRemoteDataSource> {
        CarRemoteDataSourceImpl(FirebaseFirestore.getInstance())
    }

    factory<UserRemoteDataSource> {
        UserRemoteFirebaseDataSourceImpl(
            FirebaseAuth.getInstance(),
            FirebaseFirestore.getInstance()
        )
    }

    factory<AppRepository> { AppRepositoryImpl(appRemoteDataSource = get()) }
    factory<CarRepository> { CarRepositoryImpl(carRemoteDataSource = get()) }
    factory<UserRepository> { UserRepositoryImpl(userRemoteDataSource = get()) }
}
