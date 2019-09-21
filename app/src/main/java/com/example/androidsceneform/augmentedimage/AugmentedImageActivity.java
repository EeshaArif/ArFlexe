package com.example.androidsceneform.augmentedimage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidsceneform.MainActivity;
import com.example.androidsceneform.R;
import com.example.androidsceneform.augmentedimage.sceneform.AugmentedImageNode;
import com.example.androidsceneform.augmentedimage.sceneform.AugmentedImageNode2;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Frame;
import com.google.ar.core.Pose;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.example.androidsceneform.common.helpers.SnackbarHelper;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.example.androidsceneform.augmentedimage.AugmentedImageFragment.img1;
import static com.example.androidsceneform.augmentedimage.AugmentedImageFragment.img2;
import static com.example.androidsceneform.augmentedimage.AugmentedImageFragment.img3;
import static com.example.androidsceneform.augmentedimage.AugmentedImageFragment.img4;


public class AugmentedImageActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("you need to exit to detect again:")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        AugmentedImageActivity.super.onBackPressed();
                    }
                }).create().show();
    }

    private ArFragment arFragment;
    private ImageView fitToScanView;
    private AugmentedImageNode2 node;

    // Augmented image and its associated center pose anchor, keyed by the augmented image in
    // the database.
    private final Map<AugmentedImage, AugmentedImageNode> augmentedImageMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmented_image);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        fitToScanView = findViewById(R.id.image_view_fit_to_scan);

        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (augmentedImageMap.isEmpty()) {
            fitToScanView.setVisibility(View.VISIBLE);
        }
    }


    private void onUpdateFrame(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();

        // If there is no frame or ARCore is not tracking yet, just return.
        if (frame == null || frame.getCamera().getTrackingState() != TrackingState.TRACKING) {
            return;
        }

        Collection<AugmentedImage> updatedAugmentedImages =
                frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage augmentedImage : updatedAugmentedImages) {
            switch (augmentedImage.getTrackingState()) {
                case PAUSED:
                    // When an image is in PAUSED state, but the camera is not PAUSED, it has been detected,
                    // but not yet tracked.
                    String text = "Detected Image " + augmentedImage.getIndex();
                    SnackbarHelper.getInstance().showMessage(this, text);
                    break;

                case TRACKING:

                    fitToScanView.setVisibility(View.GONE);

                    // Create a new anchor for newly found images.
                    if (!augmentedImageMap.containsKey(augmentedImage)) {


                        if (augmentedImage.getIndex()==img1) {
                            AugmentedImageNode node1 = new AugmentedImageNode(this,"skeleton.sfb");

                            node1.setImage(augmentedImage);
                          /*  Pose pose = Pose.makeTranslation(0.0f, 0.2f, 0.0f);
                            node1.setLocalRotation(new Quaternion(pose.qx(), pose.qy(), pose.qz(), pose.qw()));
                            node1.setLocalPosition(new Vector3(pose.tx(), pose.ty(), pose.tz()));
                            augmentedImageMap.put(augmentedImage, node1);*/
                            arFragment.getArSceneView().getScene().addChild(node1);
                            node1.setOnTapListener(new Node.OnTapListener() {
                                @Override
                                public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {


                                        ViewRenderable.builder().setView(getBaseContext(), R.layout.skeleton_details)
                                                .setHorizontalAlignment(ViewRenderable.HorizontalAlignment.LEFT)
                                                .build().thenAccept(ViewRenderable ->
                                        {
                                            TransformableNode nameView = new TransformableNode(arFragment.getTransformationSystem());
                                            nameView.setLocalPosition(new Vector3(0f,  0.0f/*model.getLocalScale().y*/, -0.10f));

                                            nameView.setParent(node1);
                                            nameView.setRenderable(ViewRenderable);
                                            nameView.select();

                                            //setText
                                            TextView txt_name = (TextView) ViewRenderable.getView();
                                            txt_name.setText("frontal bone\n" +
                                                    "parietal bone (2)\n" +
                                                    "temporal bone (2)\n" +
                                                    "occipital bone\n" +
                                                    "sphenoid bone\n" +
                                                    "ethmoid bone\n" +
                                                    "mandible\n" +
                                                    "maxilla (2)\n" +
                                                    "palatine bone (2)\n"
                                                    +"vomer\n" +
                                                    "inferior nasal conchae (2)\n" +
                                                    "Ear Bones (6):\n" +
                                                    "\n" +
                                                    "malleus (2)\n" +
                                                    "incus (2)\n" +
                                                    "stapes (2)\n" +
                                                    "Throat Bone (1):\n" +
                                                    "\n" +
                                                    "hyoid bone\n" +
                                                    "Shoulder Bones (4):\n" +
                                                    "\n" +
                                                    "scapula or shoulder blade (2)\n" +
                                                    "clavicle or collarbone (2)\n" +
                                                    "Chest Bones (25):\n" +
                                                    "\n" +
                                                    "sternum (1)\n" +
                                                    "ribs (2 x 12)\n" +
                                                    "Vertebral Bones (26):\n" +
                                                    "\n" +
                                                    "cervical vertebrae (7)\n" +
                                                    "thoracic vertebrae (12)\n" +
                                                    "lumbar vertebrae (5)\n" +
                                                    "sacral vertebrae (1)\n" +
                                                    "coccygeal vertebrae (1)\n" +
                                                    "Arm and Forearm Bones (6):\n" +
                                                    "\n" +
                                                    "Humerus (2)\n" +
                                                    "radius (2)\n" +
                                                    "ulna (2)\n" +
                                                    "Hand Bones (54):\n" +
                                                    "Carpal (wrist) bones:\n" +
                                                    "scaphoid bone (2)\n" +
                                                    "lunate bone (2)\n" +
                                                    "triquetral bone (2)\n" +
                                                    "pisiform bone (2)\n" +
                                                    "trapezium (2)\n" +
                                                    "trapezoid bone (2)\n" +
                                                    "capitate bone (2)\n" +
                                                    "hamate bone (2)\n" +
                                                    "Metacarpus (palm) bones:\n" +
                                                    "metacarpal bones (5 × 2)\n" +
                                                    "proximal phalanges (5 × 2)\n" +
                                                    "intermediate phalanges (4 × 2)\n" +
                                                    "distal phalanges (5 × 2)"
                                                   );
                                            nameView.setOnTapListener(new Node.OnTapListener() {
                                                @Override
                                                public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                                                    nameView.setEnabled(false);

                                                }
                                            });




                                        });


                                }
                            });

                        }
                        else  if (augmentedImage.getIndex()==img2) {
                            node = new AugmentedImageNode2(this, "bluemodel.sfb");

                            node.setImage(augmentedImage);

                            arFragment.getArSceneView().getScene().addChild(node);
                        }
                        else if (augmentedImage.getIndex()==img3) {
                             node = new AugmentedImageNode2(this, "brownmodel.sfb");

                            node.setImage(augmentedImage);

                            arFragment.getArSceneView().getScene().addChild(node);
                        }
                        else if (augmentedImage.getIndex()==img4) {

                             node = new AugmentedImageNode2(this, "yellowmodel1.sfb");

                            node.setImage(augmentedImage);

                            arFragment.getArSceneView().getScene().addChild(node);
                        }



                        }

                    break;

                case STOPPED:
                    augmentedImageMap.remove(augmentedImage);

                    break;
            }
        }
    }
}
