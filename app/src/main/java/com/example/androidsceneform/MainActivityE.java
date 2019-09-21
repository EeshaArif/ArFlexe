package com.example.androidsceneform;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivityE extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Main";
    ArFragment arFragment;
    //Every 3D model has a renderable(model maker)
    private ModelRenderable crystalRenderable;

    //Images displayed for choosing the 3D model
    ImageView back,chair1,lamp3,table1,desk1;
    View arrayView[];

    //which image to select from the HorizontalScrollView
    private int selected=0;


    @Override
    //the system calls this when creating the fragment
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To use our own layout Parameters
        setContentView(R.layout.activity_main_e);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);
        //getting the views(images) from the main activity and associating them to the variables
        back   = (ImageView) findViewById(R.id.back);
        chair1=(ImageView) findViewById(R.id.chair1);
        lamp3=(ImageView) findViewById(R.id.lamp3);
        table1 = (ImageView) findViewById(R.id.table1);
        desk1 = (ImageView) findViewById(R.id.desk1);


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
                .setSource(this,R.raw.crystal)
                .build().thenAccept(renderable -> crystalRenderable=renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this,"unable to load crystal model",Toast.LENGTH_SHORT).show();

                            return null;});




    }



    private void createModel(AnchorNode anchornode, int selected) {
       if (selected==0){
            TransformableNode crystal=new TransformableNode(arFragment.getTransformationSystem());
            crystal.setParent(anchornode);

            crystal.getScaleController().setMaxScale(25f);
            crystal.getScaleController().setMinScale(20f);
            crystal.setRenderable(crystalRenderable);
            crystal.select();

            addName(anchornode,crystal,"WELCOME TO ARflexe :\n"+
                    "where shopping is mixed with reality\n"
            +"Experience your items right at home without even buying them\n"+
                    "!An experience you'll never forget! ");

        }





    }
    //this method is being used inside createModel() to add a specific name to each model
    private void addName(AnchorNode anchornode, TransformableNode model, String name) {
        //each model we will create each name
        ViewRenderable.builder().setView(this, R.layout.name_animal)
                .setHorizontalAlignment(ViewRenderable.HorizontalAlignment.LEFT)
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

            //click to textview to remove animal
            txt_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anchornode.setParent(null);
                    Log.d(TAG,anchornode.toString());
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
                chair1,lamp3,table1,desk1
        };
    }
    //This method will be called after setting onClickListener to the members of arrayView
    @Override
    //The view parameter refers to the element previously clicked.
    // Each views which are attached to the Listener are contained in this parameter
    public void onClick(View view/*The view that was clicked*/) {

        //To know which view has been clicked and perform some actions regarding them, The getId() has been used to retrieve the exact view
        if(view.getId()==R.id.chair1) {
            setBackground(view.getId());

            Intent newActivity= new Intent(this, Chairs.class);
            startActivity(newActivity);

        }
        else if(view.getId()==R.id.lamp3){
            setBackground(view.getId());
            Intent intent= new Intent(this,Lamps.class);
            startActivity(intent);


        }
        else if(view.getId()==R.id.table1){
            setBackground(view.getId());
            Intent intent= new Intent(this,Tables.class);
            startActivity(intent);


        }
        else if(view.getId()==R.id.desk1){
            setBackground(view.getId());
            Intent intent= new Intent(this,Desks.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.back){
            setBackground(view.getId());
            Intent intent= new Intent(this,MainActivity.class);
            startActivity(intent);
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