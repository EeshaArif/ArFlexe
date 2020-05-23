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

public class  Chairs extends AppCompatActivity implements View.OnClickListener {
    private TransformableNode nameView;

    ArFragment arFragment;
    //Every 3D model has a renderable(model maker)
    private ModelRenderable chair1Renderable,
            chair2Renderable,
            chair3Renderable,
            chair4Renderable,
            chair5Renderable;

    //Images displayed for choosing the 3D model
    ImageView back,chair1,chair2,chair3,chair4,chair5;
    View arrayView[];

    //which image to select from the HorizontalScrollView
    private int selected=1;


    @Override
    //the system calls this when creating the fragment
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To use our own layout Parameters
        setContentView(R.layout.activity_chairs);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);
        //getting the views(images) from the main activity and associating them to the variables
        back   = (ImageView) findViewById(R.id.back);
        chair1 = (ImageView) findViewById(R.id.chair1);
        chair2 = (ImageView) findViewById(R.id.chair2);
        chair3 = (ImageView) findViewById(R.id.chair3);
        chair4 = (ImageView) findViewById(R.id.chair4);
        chair5 = (ImageView) findViewById(R.id.chair5);

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
                .setSource(this,R.raw.chair1)
                .build().thenAccept(renderable -> chair1Renderable=renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this,"unable to load chair1 model",Toast.LENGTH_SHORT).show();

                            return null;});

        ModelRenderable.builder()
                .setSource(this,R.raw.chair2)
                .build().thenAccept(renderable -> chair2Renderable=renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this,"unable to load Chair2 model",Toast.LENGTH_SHORT).show();

                            return null;});

        ModelRenderable.builder()
                .setSource(this,R.raw.chair3)
                .build().thenAccept(renderable -> chair3Renderable=renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this,"unable to load chair3 model",Toast.LENGTH_SHORT).show();

                            return null;});

        ModelRenderable.builder()
                .setSource(this,R.raw.chair4)
                .build().thenAccept(renderable -> chair4Renderable=renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this,"unable to load chair4 model",Toast.LENGTH_SHORT).show();

                            return null;});

        ModelRenderable.builder()
                .setSource(this,R.raw.chair5)
                .build().thenAccept(renderable -> chair5Renderable=renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this,"unable to load chair5 model",Toast.LENGTH_SHORT).show();

                            return null;});

    }



    private void createModel(AnchorNode anchornode, int selected) {
        if (selected==1){
            TransformableNode chair1=new TransformableNode(arFragment.getTransformationSystem());
            chair1.setParent(anchornode);

            chair1.getScaleController().setMaxScale(25f);
            chair1.getScaleController().setMinScale(20f);
            chair1.setRenderable(chair1Renderable);
            chair1.select();

            addName(anchornode,chair1,"Zurich Single Seater Sofa\n" +
                    "Rs 8,500\n" +
                    "\n" +
                    "Delivery Time: Immediate\n" +
                    "\n" +
                    "Delivery Charges: Yes\n" +
                    "\n" +
                    "Warranty: 6 Months\n" +
                    "\n" +
                    "Frame Material: Wooden\n" +
                    "\n" +
                    "Style/Design: Standard, Modern\n" +
                    "\n" +
                    "Back Style: Cushion Back");

        }
        if (selected==2){
            TransformableNode chair2=new TransformableNode(arFragment.getTransformationSystem());
            chair2.setParent(anchornode);
            chair2.getScaleController().setMaxScale(25f);
            chair2.getScaleController().setMinScale(20f);
            chair2.setRenderable(chair2Renderable);
            chair2.select();

            addName(anchornode,chair2,"design in progress");

        }
        if (selected==3){
            TransformableNode chair3=new TransformableNode(arFragment.getTransformationSystem());
            chair3.setParent(anchornode);
            chair3.getScaleController().setMaxScale(25f);
            chair3.getScaleController().setMinScale(20f);
            chair3.setRenderable(chair3Renderable);
            chair3.select();

            addName(anchornode,chair3,"Single Seater Canvas Sofa\n"+
                    "Rs 10,000/unit\n"+
                    "Leather Furniture");

        }
        if (selected==4){
            TransformableNode chair4=new TransformableNode(arFragment.getTransformationSystem());
            chair4.setParent(anchornode);
            chair4.getScaleController().setMaxScale(25f);
            chair4.getScaleController().setMinScale(20f);
            chair4.setRenderable(chair4Renderable);
            chair4.select();

            addName(anchornode,chair4,"Fixed styled Chair\n" +
                    "Rs 12,000/Unit\n" +
                    "Sai Ram Furniture");

        }
        if (selected==5){
            TransformableNode chair5=new TransformableNode(arFragment.getTransformationSystem());

            chair5.getScaleController().setMaxScale(25f);
            chair5.getScaleController().setMinScale(20f);
            chair5.setParent(anchornode);
            chair5.setRenderable(chair5Renderable);
            chair5.select();

            addName(anchornode,chair5,"Wooden Indoor Chair\n" +
                    "Rs 3,000/Piece\n" +
                    "Famous Furniture & Interior Contractor");


        }

    }
    private void setNameView(TransformableNode nameView){
        this.nameView=nameView;
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


                 
                }



            });



        });
    }
    //Using setOnClickListener to all the members of the arrayView(All the images in the horizontal scroll)
    private void setClickListener() {
        for(int i=0;i<arrayView.length;i++)
            arrayView[i].setOnClickListener(this);
    }
    //This method creates the View in the main app(the horizontal scroll)
    private void setArrayView() {
        arrayView=new View[]{
                back,chair1,chair2,chair3,chair4,chair5
        };
    }
    //This method will be called after setting onClickListener to the members of arrayView
    @Override
    //The view parameter refers to the element previously clicked.
    // Each views which are attached to the Listener are contained in this parameter
    public void onClick(View view/*The view that was clicked*/) {

        //To know which view has been clicked and perform some actions regarding them, The getId() has been used to retrieve the exact view
        if (view.getId()==R.id.back){
            Intent newActivity= new Intent(this, MainActivityE.class);
            startActivity(newActivity);
        }
        else if(view.getId()==R.id.chair1) {
            selected = 1;
            setBackground(view.getId());
        }
        else if(view.getId()==R.id.chair2){
            selected=2;
            setBackground(view.getId());
        }
        else if(view.getId()==R.id.chair3){
            selected=3;
            setBackground(view.getId());
        }
        else if(view.getId()==R.id.chair4){
            selected=4;
            setBackground(view.getId());
        }
        else if(view.getId()==R.id.chair5){

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
