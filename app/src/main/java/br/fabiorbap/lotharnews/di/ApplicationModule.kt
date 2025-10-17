package br.fabiorbap.lotharnews.di

import br.fabiorbap.lotharnews.BuildConfig
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan(BuildConfig.APPLICATION_ID)
class ApplicationModule