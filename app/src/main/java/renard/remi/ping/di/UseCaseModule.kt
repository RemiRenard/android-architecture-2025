package renard.remi.ping.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import renard.remi.ping.domain.repository.AuthRepository
import renard.remi.ping.domain.repository.SettingsRepository
import renard.remi.ping.domain.repository.UserRepository
import renard.remi.ping.domain.use_case.CreateAccountUseCase
import renard.remi.ping.domain.use_case.GetDynamicsColorsUseCase
import renard.remi.ping.domain.use_case.GetIsInDarkModeUseCase
import renard.remi.ping.domain.use_case.GetMeUseCase
import renard.remi.ping.domain.use_case.GetPaletteColorsUseCase
import renard.remi.ping.domain.use_case.LoginUseCase
import renard.remi.ping.domain.use_case.LogoutUseCase
import renard.remi.ping.domain.use_case.UpdateAppColorsUseCase
import renard.remi.ping.domain.use_case.UpdateDarkModeUseCase
import renard.remi.ping.domain.use_case.UpdateFcmTokenUseCase
import renard.remi.ping.domain.use_case.UseDynamicsColorsUseCase
import renard.remi.ping.domain.use_case.ValidatePasswordUseCase
import renard.remi.ping.domain.use_case.ValidateUsernameUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideValidateUsernameUseCase() = ValidateUsernameUseCase()

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase() = ValidatePasswordUseCase()

    @Provides
    @Singleton
    fun provideLoginUseCase(
        authRepository: AuthRepository
    ): LoginUseCase {
        return LoginUseCase(
            authRepository = authRepository
        )
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        authRepository: AuthRepository
    ): LogoutUseCase {
        return LogoutUseCase(
            authRepository = authRepository
        )
    }

    @Provides
    @Singleton
    fun provideCreateAccountUseCase(
        authRepository: AuthRepository
    ): CreateAccountUseCase {
        return CreateAccountUseCase(
            authRepository = authRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetMeUseCase(
        userRepository: UserRepository
    ): GetMeUseCase {
        return GetMeUseCase(
            userRepository = userRepository
        )
    }

    @Provides
    @Singleton
    fun provideUpdateFcmTokenUseCase(
        userRepository: UserRepository
    ): UpdateFcmTokenUseCase {
        return UpdateFcmTokenUseCase(
            userRepository = userRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetDynamicsColorsUseCase(
        settingsRepository: SettingsRepository
    ): GetDynamicsColorsUseCase {
        return GetDynamicsColorsUseCase(
            settingsRepository = settingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetIsInDarkModeUseCase(
        settingsRepository: SettingsRepository
    ): GetIsInDarkModeUseCase {
        return GetIsInDarkModeUseCase(
            settingsRepository = settingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetPaletteColorUseCase(
        settingsRepository: SettingsRepository
    ): GetPaletteColorsUseCase {
        return GetPaletteColorsUseCase(
            settingsRepository = settingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideUpdateAppColorsUseCase(
        settingsRepository: SettingsRepository
    ): UpdateAppColorsUseCase {
        return UpdateAppColorsUseCase(
            settingsRepository = settingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideUpdateDarkModeUseCase(
        settingsRepository: SettingsRepository
    ): UpdateDarkModeUseCase {
        return UpdateDarkModeUseCase(
            settingsRepository = settingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideUseDynamicsColorsUseCase(
        settingsRepository: SettingsRepository
    ): UseDynamicsColorsUseCase {
        return UseDynamicsColorsUseCase(
            settingsRepository = settingsRepository
        )
    }
}