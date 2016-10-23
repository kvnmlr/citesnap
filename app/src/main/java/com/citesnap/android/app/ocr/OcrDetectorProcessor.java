/*
 * Copyright (C) The Android Open Source Project
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
package com.citesnap.android.app.ocr;

import android.util.SparseArray;
import android.widget.ArrayAdapter;

import com.citesnap.android.app.camera.GraphicOverlay;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;

/**
 * A very simple Processor which receives detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    private GraphicOverlay<OcrGraphic> graphicOverlays;
    private ArrayList<String> texts;
    private ArrayList<OcrGraphic> graphics;
    private boolean selectionOkay;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay) {
        graphicOverlays = ocrGraphicOverlay;
        graphics = new ArrayList<>();
    }

    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        if (selectionOkay) {
            return;
        }
        texts = new ArrayList<>();
        graphicOverlays.clear();
        ArrayList<OcrGraphic> graphicsTemp = new ArrayList<>();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            texts.add(item.getValue());
            OcrGraphic graphic = new OcrGraphic(graphicOverlays, item);

            if (checkOld(item) != null) {
                if (checkOld(item).isActivated()) {
                    graphic.activate();
                }
            }
            graphicsTemp.add(graphic);
            graphicOverlays.add(graphic);
        }
        graphics = graphicsTemp;

    }

    /**
     * Frees the resources associated with this detection processor.
     */
    @Override
    public void release() {
        graphicOverlays.clear();
    }

    public ArrayList<String> getTexts() {
        return texts;
    }

    public void setSelectionOkay(boolean okay) {
        selectionOkay = okay;
    }
    public ArrayList<OcrGraphic> getGraphics(boolean topToBottom) {
        if (topToBottom) {
            // TODO order
        }
        return graphics;
    }
    private OcrGraphic checkOld(TextBlock c) {
        for (OcrGraphic cOld : graphics) {
            if (c.getValue().contains(cOld.getTextBlock().getValue())
                    || cOld.getTextBlock().getValue().contains(c.getValue())
                    || cOld.getTextBlock().getValue().equals(c.getValue())) {
                return cOld;
            }
        }
        return null;
    }
}
