/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.androidsceneform.augmentedimage;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.example.androidsceneform.common.helpers.SnackbarHelper;
import com.google.ar.sceneform.ux.ArFragment;

import java.io.IOException;
import java.io.InputStream;


public class AugmentedImageFragment extends ArFragment {
  private static final String TAG = "AugmentedImageFragment";


  private static final String DEFAULT_IMAGE_NAME = "sskeleton.jpg";


  private static final boolean USE_SINGLE_IMAGE = true;
  static int img1;
  static int img2;
  static int img3;
  static int img4;



  private static final double MIN_OPENGL_VERSION = 3.0;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);


    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
      Log.e(TAG, "Sceneform requires Android N or later");
      SnackbarHelper.getInstance()
          .showError(getActivity(), "Sceneform requires Android N or later");
    }

    String openGlVersionString =
        ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
            .getDeviceConfigurationInfo()
            .getGlEsVersion();
    if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
      Log.e(TAG, "Sceneform requires OpenGL ES 3.0 or later");
      SnackbarHelper.getInstance()
          .showError(getActivity(), "Sceneform requires OpenGL ES 3.0 or later");
    }
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = super.onCreateView(inflater, container, savedInstanceState);

    // Turn off the plane discovery since we're only looking for images
    getPlaneDiscoveryController().hide();
    getPlaneDiscoveryController().setInstructionView(null);
    getArSceneView().getPlaneRenderer().setEnabled(false);
    return view;
  }

  @Override
  protected Config getSessionConfiguration(Session session) {
    Config config = super.getSessionConfiguration(session);
    if (!setupAugmentedImageDatabase(config, session)) {
      SnackbarHelper.getInstance()
          .showError(getActivity(), "Could not setup augmented image database");
    }
    return config;
  }

  private boolean setupAugmentedImageDatabase(Config config, Session session) {
    AugmentedImageDatabase augmentedImageDatabase;

    AssetManager assetManager = getContext() != null ? getContext().getAssets() : null;
    if (assetManager == null) {
      Log.e(TAG, "Context is null, cannot intitialize image database.");
      return false;
    }


    if (USE_SINGLE_IMAGE) {
      Bitmap augmentedImageBitmap = loadAugmentedImageBitmap(assetManager);
      if (augmentedImageBitmap == null) {
        return false;
      }
      Bitmap augmentedImageBitmap2=null;
      try (InputStream is = assetManager.open("blueimage4.jpg")) {
        augmentedImageBitmap2= BitmapFactory.decodeStream(is);
      } catch (IOException e) {
        Log.e(TAG, "IO exception loading augmented image bitmap.", e);
      }
      if (augmentedImageBitmap2 == null) {
        return false;
      }

      Bitmap augmentedImageBitmap3=null;
      try (InputStream is = assetManager.open("brownimage1.jpg")) {
        augmentedImageBitmap3= BitmapFactory.decodeStream(is);
      } catch (IOException e) {
        Log.e(TAG, "IO exception loading augmented image bitmap.", e);
      }
      if (augmentedImageBitmap3 == null) {
        return false;
      }
      Bitmap augmentedImageBitmap4=null;
      try (InputStream is = assetManager.open("yellowimage1.jpg")) {
        augmentedImageBitmap4= BitmapFactory.decodeStream(is);
      } catch (IOException e) {
        Log.e(TAG, "IO exception loading augmented image bitmap.", e);
      }
      if (augmentedImageBitmap4== null) {
        return false;
      }


      augmentedImageDatabase = new AugmentedImageDatabase(session);
      img1=augmentedImageDatabase.addImage(DEFAULT_IMAGE_NAME, augmentedImageBitmap);
      img2=augmentedImageDatabase.addImage("blueimage4.jpg", augmentedImageBitmap2);
      img3=augmentedImageDatabase.addImage("brownimage1.jpg", augmentedImageBitmap3);
      img4=augmentedImageDatabase.addImage("yellowimage1.jpg", augmentedImageBitmap4);


    config.setAugmentedImageDatabase(augmentedImageDatabase);}
    return true;
  }

  private Bitmap loadAugmentedImageBitmap(AssetManager assetManager) {
    try (InputStream is = assetManager.open(DEFAULT_IMAGE_NAME)) {
      return BitmapFactory.decodeStream(is);
    } catch (IOException e) {
      Log.e(TAG, "IO exception loading augmented image bitmap.", e);
    }
    return null;
  }
}
