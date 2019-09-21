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

public class  Tables extends AppCompatActivity implements View.OnClickListener {
    private TransformableNode nameView;

    ArFragment arFragment;
    //Every 3D model has a renderable(model maker)
    private ModelRenderable table1Renderable,
            tabele2Renderable;



    //Images displayed for choosing the 3D model
    ImageView back, table1, table2;
    View arrayView[];

    //which image to select from the HorizontalScrollView
    private int selected=1;


    @Override
    //the system calls this when creating the fragment
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To use our own layout Parameters
        setContentView(R.layout.activity_tables);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);
        //getting the views(images) from the main activity and associating them to the variables
        back   = (ImageView) findViewById(R.id.back);
        table1 = (ImageView) findViewById(R.id.table1);
        table2 = (ImageView) findViewById(R.id.table2);


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
                .setSource(this,R.raw.table1)
                .build().thenAccept(renderable -> table1Renderable =renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this,"unable to load table1 model",Toast.LENGTH_SHORT).show();

                            return null;});

        ModelRenderable.builder()
                .setSource(this,R.raw.table2)
                .build().thenAccept(renderable -> tabele2Renderable =renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this,"unable to load table2 model",Toast.LENGTH_SHORT).show();

                            return null;});


    }



    private void createModel(AnchorNode anchornode, int selected) {
        if (selected==1){
            TransformableNode table1=new TransformableNode(arFragment.getTransformationSystem());
            table1.setParent(anchornode);

            table1.getScaleController().setMaxScale(25f);
            table1.getScaleController().setMinScale(20f);
            table1.setRenderable(table1Renderable);
            table1.select();

            addName(anchornode,table1,"Brwon Solid Wood Vintage Cart Coffee Table\n" +
                    "Rs 20,000\n" +
                    "Color: Brown\n" +
                    "\n" +
                    "Surface Finish: Natural\n" +
                    "\n" +
                    "Appearance: Vintage\n" +
                    "\n" +
                    "Shape: Rectangular\n" +
                    "\n" +
                    "Finish Type: Polish");

        }
        if (selected==2){
            TransformableNode table2=new TransformableNode(arFragment.getTransformationSystem());
            table2.setParent(anchornode);
            table2.getScaleController().setMaxScale(25f);
            table2.getScaleController().setMinScale(20f);
            table2.setRenderable(tabele2Renderable);
            table2.select();

            addName(anchornode,table2,"Dom Glass Glass Table\n" +
                    "Rs 276/ Square Feet\n" +
                    "\n" +
                    "Brand: Dom Glass\n" +
                    "\n" +
                    "Material: Glass\n" +
                    "\n" +
                    "Color: Transparent\n" +
                    "\n" +
                    "No Of Legs: 4\n" +
                    "\n" +
                    "Glass Thickness: 10-20 mm");

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
            nameView.setLocalPosition(new Vector3(0f,  1.0f/*model.getLocalScale().y*/, 0f));
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
            arrayView[i].setOnClickListener(this);
    }
    //This method creates the View in the main app(the horizontal scroll)
    private void setArrayView() {
        arrayView=new View[]{
                back, table1, table2
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
        else if(view.getId()==R.id.table1) {
            selected = 1;
            setBackground(view.getId());
        }
        else if(view.getId()==R.id.table2){
            selected=2;
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
