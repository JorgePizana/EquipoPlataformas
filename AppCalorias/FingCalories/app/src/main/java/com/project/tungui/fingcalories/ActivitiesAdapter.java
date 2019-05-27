package com.project.tungui.fingcalories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.location.DetectedActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class ActivitiesAdapter extends ArrayAdapter<DetectedActivity> {

    // Referencia a la base de datos de Firebase
    private DatabaseReference data_activity_recog;

    ActivitiesAdapter(Context context,
                      ArrayList<DetectedActivity> detectedActivities) {
        super(context, 0, detectedActivities);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        // Retrieve the data item
        DetectedActivity detectedActivity = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.detected_activity, parent, false);
        }

        // Retrieve the TextViews where we’ll display the activity type, and percentage
        TextView activityName = (TextView) view.findViewById(R.id.activity_type);
        TextView activityConfidenceLevel = (TextView) view.findViewById(
                R.id.confidence_percentage);

        // If an activity is detected
        if (detectedActivity != null) {

            // Se obtiene el tipo de Actividad
            activityName.setText(ActivityIntentService.getActivityString(getContext(),
                    detectedActivity.getType()));

            // Porcentaje de la Actividad
            activityConfidenceLevel.setText(getContext().getString(R.string.percentage,
                    detectedActivity.getConfidence()));

        }
        return view;
    }

    // Process the list of detected activities
    void updateActivities(ArrayList<DetectedActivity> detectedActivities) {

        HashMap<Integer, Integer> detectedActivitiesMap = new HashMap<>();

        for (DetectedActivity activity : detectedActivities) {
            detectedActivitiesMap.put(activity.getType(), activity.getConfidence());
        }

        // Variables necesarias para enviar los Datos a Firebase
        data_activity_recog = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> data_activity = new HashMap<>();

        ArrayList<DetectedActivity> temporaryList = new ArrayList<>();
        for (int i = 0; i < ActivityIntentService.POSSIBLE_ACTIVITIES.length; i++) {
            int confidence = detectedActivitiesMap.containsKey(ActivityIntentService.POSSIBLE_ACTIVITIES[i]) ?
                    detectedActivitiesMap.get(ActivityIntentService.POSSIBLE_ACTIVITIES[i]) : 0;

            // Add the object to a temporaryList
            temporaryList.add(new
                    DetectedActivity(ActivityIntentService.POSSIBLE_ACTIVITIES[i],
                    confidence));

            data_activity.put(ActivityIntentService.getActivityString(getContext(),
                    ActivityIntentService.POSSIBLE_ACTIVITIES[i]), confidence);
        }

        // Se envian los datos a Firebase
        data_activity_recog.child("Activity Recognition").push().setValue(data_activity);

        // Remove all elements from the temporaryList
        this.clear();

        // Refresh the View
        for (DetectedActivity detectedActivity: temporaryList) {
            this.add(detectedActivity);
        }
    }
}