package com.example.androidsceneform;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class Lamps extends AppCompatActivity {
    private TransformableNode nameView;

    ArFragment arFragment;
    //Every 3D model has a renderable(model maker)
    private ModelRenderable lamp1Renderable,
            lamp2Renderable,
            lamp3Renderable,
            lamp4Renderable,
            lamp5Renderable;

    //Images displayed for choosing the 3D model
    ImageView back, lamp1, lamp2, lamp3, lamp4, lamp5;
    View arrayView[];

    //which image to select from the HorizontalScrollView
    private int selected=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamps);




        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);
        //getting the views(images) from the main activity and associating them to the variables
        back   = (ImageView) findViewById(R.id.back);
        lamp1 = (ImageView) findViewById(R.id.lamp1);
        lamp2 = (ImageView) findViewById(R.id.lamp2);
        lamp3 = (ImageView) findViewById(R.id.lamp3);
        lamp4 = (ImageView) findViewById(R.id.lamp4);
        lamp5 = (ImageView) findViewById(R.id.lamp5);

        //Implementing the self defined methods
        setArrayView();
        setClickListener();
        setupModel();
        //Look for a plane to detect
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            //When the detected plane has been tapped then do the following
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {

                //creates a description of a fixed location and orientation in the real world
                Anchor anchor = hitResult.createAnchor();
                //The node is positioned according to the anchor
                AnchorNode anchornode = new AnchorNode(anchor);
                //*****************
                anchornode.setParent(arFragment.getArSceneView().getScene());
                //using our own defined method
                createModel(anchornode, selected);


            }
        });
    }
    //building the 3D  models
    private void setupModel() {

        ModelRenderable.builder()
                .setSource(this,R.raw.lamp1)
                .build().thenAccept(renderable -> lamp1Renderable =renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this,"unable to load lamp1 model",Toast.LENGTH_SHORT).show();

                            return null;});

        ModelRenderable.builder()
                .setSource(this,R.raw.lamp2)
                .build().thenAccept(renderable -> lamp2Renderable =renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this,"unable to load lamp2 model",Toast.LENGTH_SHORT).show();

                            return null;});

        ModelRenderable.builder()
                .setSource(this,R.raw.lamp3)
                .build().thenAccept(renderable -> lamp3Renderable =renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this,"unable to load lamp3 model",Toast.LENGTH_SHORT).show();

                            return null;});

        ModelRenderable.builder()
                .setSource(this,R.raw.lamp4)
                .build().thenAccept(renderable -> lamp4Renderable =renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this,"unable to load lamp4 model",Toast.LENGTH_SHORT).show();

                            return null;});

        ModelRenderable.builder()
                .setSource(this,R.raw.lamp5)
                .build().thenAccept(renderable -> lamp5Renderable =renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this,"unable to load lamp5 model",Toast.LENGTH_SHORT).show();

                            return null;});

    }



    private void createModel(AnchorNode anchornode, int selected) {
        if (selected==1){
            TransformableNode lamp1=new TransformableNode(arFragment.getTransformationSystem());
            lamp1.setParent(anchornode);

            lamp1.getScaleController().setMaxScale(25f);
            lamp1.getScaleController().setMinScale(20f);
            lamp1.setRenderable(lamp1Renderable);
            lamp1.select();

            addName(anchornode,lamp1,"Modern/Contemporary Acrylic Antique Brass Table Lamp\n" +
                    "Rs 500\n" +
                    "\n" +
                    "Base Material: Brass\n" +
                    "\n" +
                    "Style: Modern/Contemporary\n" +
                    "\n" +
                    "Shade Material: Acrylic\n" +
                    "\n" +
                    "Power Source: Electric\n" +
                    "\n" +
                    "Usage: Decoration");

        }
        if (selected==2){
            TransformableNode lamp2=new TransformableNode(arFragment.getTransformationSystem());
            lamp2.setParent(anchornode);
            lamp2.getScaleController().setMaxScale(25f);
            lamp2.getScaleController().setMinScale(20f);
            lamp2.setRenderable(lamp2Renderable);
            lamp2.select();

            addName(anchornode,lamp2,"Wellness Table Lamp\n" +
                    "Rs 2,550/Piece\n" +
                    "NS Jain & Co. Private Limited");

        }
        if (selected==3){
            TransformableNode lamp3=new TransformableNode(arFragment.getTransformationSystem());
            lamp3.setParent(anchornode);
            lamp3.getScaleController().setMaxScale(25f);
            lamp3.getScaleController().setMinScale(20f);
            lamp3.setRenderable(lamp3Renderable);
            lamp3.select();

            addName(anchornode,lamp3,"Tashhir Table Lamp Multi-Color"+"• Imported.\n" +
                    "• Pair of Table Lamp with shades.\n" +
                    "• Marble.\n" +
                    "• Multi Color.");

        }
        if (selected==4){
            TransformableNode lamp4=new TransformableNode(arFragment.getTransformationSystem());
            lamp4.setParent(anchornode);
            lamp4.getScaleController().setMaxScale(25f);
            lamp4.getScaleController().setMinScale(20f);
            lamp4.setRenderable(lamp4Renderable);
            lamp4.select();

            addName(anchornode,lamp4,"Aluminium Portable Lamp\n" +
                    "Rs 450/Piece\n" +
                    "Kdream Enterprises");

        }
        if (selected==5){
            TransformableNode lamp5=new TransformableNode(arFragment.getTransformationSystem());

            lamp5.getScaleController().setMaxScale(25f);
            lamp5.getScaleController().setMinScale(20f);
            lamp5.setParent(anchornode);
            lamp5.setRenderable(lamp5Renderable);
            lamp5.select();

            addName(anchornode,lamp5,"Wellness Table Lamp\n" +
                    "Rs 2,550/Piece\n" +
                    "NS Jain & Co. Private Limited");
          /*  lamp5.setOnTapListener(new Node.OnTapListener() {
                @Override
                public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                    getNameView().setEnabled(false);
                }
            });
*/


        }

    }
    private void setNameView(TransformableNode nameView){
        this.nameView=nameView;
    }
    private TransformableNode getNameView(){
        return nameView;
    }

    //this method is being used inside createModel() to add a specific name to each model
    private void addName(AnchorNode anchornode, TransformableNode model, String name) {
        //each model we will create each name
        ViewRenderable.builder().setView(this, R.layout.name_animal)
                .setHorizontalAlignment(ViewRenderable.HorizontalAlignment.RIGHT)
                .build().thenAccept(ViewRenderable ->
        {
            TransformableNode nameView = new TransformableNode(arFragment.getTransformationSystem());
            nameView.setLocalPosition(new Vector3(0f,  0.6f/*model.getLocalScale().y*/, 0f));
            nameView.setParent(anchornode);
            nameView.setRenderable(ViewRenderable);
            nameView.select();

            //setText
            TextView txt_name = (TextView) ViewRenderable.getView();
            txt_name.setText(name);
            setNameView(nameView);

            //click textview to remove animal
            txt_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anchornode.setParent(null);


                    //Log.d(TAG,anchornode.toString());
                }



            });



        });
    }
    //Using setOnClickListener to all the members of the arrayView(All the images in the horizontal scroll)
    private void setClickListener() {
        for(int i=0;i<arrayView.length;i++)
            arrayView[i].setOnClickListener(this::onClick);
    }
    //This method creates the View in the main app(the horizontal scroll)
    private void setArrayView() {
        arrayView=new View[]{
                back, lamp1, lamp2, lamp3, lamp4, lamp5
        };
    }
    //This method will be called after setting onClickListener to the members of arrayView

    //The view parameter refers to the element previously clicked.
    // Each views which are attached to the Listener are contained in this parameter

    public void onClick(View view/*The view that was clicked*/) {

        //To know which view has been clicked and perform some actions regarding them, The getId() has been used to retrieve the exact view
        if (view.getId()==R.id.back){
            Intent newActivity= new Intent(this, MainActivityE.class);
            startActivity(newActivity);
        }
        else if(view.getId()==R.id.lamp1) {
            selected = 1;
            setBackground(view.getId());
        }
        else if(view.getId()==R.id.lamp2){
            selected=2;
            setBackground(view.getId());
        }
        else if(view.getId()==R.id.lamp3){
            selected=3;
            setBackground(view.getId());
        }
        else if(view.getId()==R.id.lamp4){
            selected=4;
            setBackground(view.getId());
        }
        else if(view.getId()==R.id.lamp5){

            selected=5;
            setBackground(view.getId());
        }


    }
    //This method is used in onClick()
    //Used to differentiate the background of the selected object compared to the unselected ones
    private void setBackground(int id) {
        for(int i=0;i<arrayView.length;i++){
            if (arrayView[i].getId()==id)
                arrayView[i].setBackgroundColor(Color.parseColor("#80333639"));//set background for selected object
            else arrayView[i].setBackgroundColor(Color.TRANSPARENT);


        }
    }
}
