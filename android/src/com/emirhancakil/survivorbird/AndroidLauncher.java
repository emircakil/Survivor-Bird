package com.emirhancakil.survivorbird;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AndroidLauncher extends AndroidApplication implements AdsController {


	private InterstitialAd mInterstitialAd;
	AdRequest adRequest = new AdRequest.Builder().build();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new SurvivorBird(this), config);

		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});

		InterstitialAd.load(this, "ca-app-pub-9895116826776540/1568709299", adRequest,
				new InterstitialAdLoadCallback() {
					@Override
					public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
						// The mInterstitialAd reference will be null until
						// an ad is loaded.
						mInterstitialAd = interstitialAd;
						mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
							@Override
							public void onAdDismissedFullScreenContent() {
								// Called when fullscreen content is dismissed.
								Log.d("TAG", "The ad was dismissed.");
							}

							@Override
							public void onAdFailedToShowFullScreenContent(AdError adError) {
								// Called when fullscreen content failed to show.
								Log.d("TAG", "The ad failed to show.");
							}

							@Override
							public void onAdShowedFullScreenContent() {
								// Called when fullscreen content is shown.
								// Make sure to set your reference to null so you don't
								// show it a second time.
								mInterstitialAd = null;
								Log.d("TAG", "The ad was shown.");
							}
						});
					}

					@Override
					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
						// Handle the error
						Log.i(TAG, loadAdError.getMessage());
						mInterstitialAd = null;
					}
				});

		loadAd();

		// ID Interstitial ca-app-pub-9895116826776540/1568709299
		// ID interstitial test ca-app-pub-3940256099942544/1033173712
		//BannerID ca-app-pub-9895116826776540/1923932515
	}

	@Override
	public void loadAd() {
		InterstitialAd.load(AndroidLauncher.this, "ca-app-pub-9895116826776540/1568709299", adRequest,
				new InterstitialAdLoadCallback() {
					@Override
					public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
						// The mInterstitialAd reference will be null until
						// an ad is loaded.
						mInterstitialAd = interstitialAd;
						mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
							@Override
							public void onAdDismissedFullScreenContent() {
								// Called when fullscreen content is dismissed.
								Log.d("TAG", "The ad was dismissed.");
							}

							@Override
							public void onAdFailedToShowFullScreenContent(AdError adError) {
								// Called when fullscreen content failed to show.
								Log.d("TAG", "The ad failed to show.");
							}

							@Override
							public void onAdShowedFullScreenContent() {
								// Called when fullscreen content is shown.
								// Make sure to set your reference to null so you don't
								// show it a second time.
								mInterstitialAd = null;
								Log.d("TAG", "The ad was shown.");
							}
						});
					}

					@Override
					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
						// Handle the error
						Log.i(TAG, loadAdError.getMessage());
						mInterstitialAd = null;
					}
				});


	}

	@Override
	public void showAd() {

		try {
			runOnUiThread(new Runnable() {
				public void run() {
					if (mInterstitialAd != null) {
						mInterstitialAd.show(AndroidLauncher.this);

					}
					else {
						AdRequest interstitialRequest = new AdRequest.Builder().build();
						InterstitialAd.load(AndroidLauncher.this, "ca-app-pub-9895116826776540/1568709299", adRequest,
								new InterstitialAdLoadCallback() {
									@Override
									public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
										// The mInterstitialAd reference will be null until
										// an ad is loaded.
										mInterstitialAd = interstitialAd;
										mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
											@Override
											public void onAdDismissedFullScreenContent() {
												// Called when fullscreen content is dismissed.
												Log.d("TAG", "The ad was dismissed.");
											}

											@Override
											public void onAdFailedToShowFullScreenContent(AdError adError) {
												// Called when fullscreen content failed to show.
												Log.d("TAG", "The ad failed to show.");
											}

											@Override
											public void onAdShowedFullScreenContent() {
												// Called when fullscreen content is shown.
												// Make sure to set your reference to null so you don't
												// show it a second time.
												mInterstitialAd = null;
												Log.d("TAG", "The ad was shown.");
											}
										});
									}

									@Override
									public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
										// Handle the error
										Log.i(TAG, loadAdError.getMessage());
										mInterstitialAd = null;
									}
								});

					}
				}
			});
		} catch (Exception e) {
		}

	}
}
