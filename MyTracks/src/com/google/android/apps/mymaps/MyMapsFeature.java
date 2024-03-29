/*
 * Copyright 2009 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.android.apps.mymaps;

import com.google.android.maps.GeoPoint;

import java.io.Serializable;
import java.util.Vector;

/**
 * MyMapsFeature contains all of the data associated with a feature in My Maps,
 * where a feature is a marker, line, or shape. Some of the data is stored in a
 * {@link MyMapsFeatureMetadata} object so that it can be more efficiently
 * transmitted to other activities.
 */
public class MyMapsFeature implements Serializable {

  private static final long serialVersionUID = 8439035544430497236L;

  /** A marker feature displays an icon at a single point on the map. */
  public static final int MARKER = 0;

  /**
   * A line feature displays a line connecting a set of points on the map.
   */
  public static final int LINE = 1;

  /**
   * A shape feature displays a border defined by connecting a set of points,
   * including connecting the last to the first, and displays the area
   * confined by this border.
   */
  public static final int SHAPE = 2;

  /** The local feature id for this feature, if needed. */
  private String androidId;

  /**
   * The latitudes of the points of this feature in order, specified in
   * millionths of a degree north.
   */
  private final Vector<Integer> latitudeE6 = new Vector<Integer>();

  /**
   * The longitudes of the points of this feature in order, specified in
   * millionths of a degree east.
   */
  private final Vector<Integer> longitudeE6 = new Vector<Integer>();

  /** The metadata of this feature in a format efficient for transmission. */
  private MyMapsFeatureMetadata featureInfo = new MyMapsFeatureMetadata();

  /**
   * Initializes a valid but empty feature. It will default to a
   * {@link #MARKER} with a blue placemark with a dot as an icon at the
   * location (0, 0).
   */
  public MyMapsFeature() {
  }

  /**
   * Adds a new point to the end of this feature.
   *
   * @param point The new point to add
   */
  public synchronized void addPoint(GeoPoint point) {
    latitudeE6.add(point.getLatitudeE6());
    longitudeE6.add(point.getLongitudeE6());
  }

  /**
   * Generates a new local id for this feature based on the current time and
   * a random number.
   */
  public synchronized void generateAndroidId() {
    Long time = System.currentTimeMillis();
    Integer random = (int) (Math.random() * 10000);
    androidId = time.toString() + "." + random.toString();
  }

  /**
   * Retrieves the current local id for this feature if one is available.
   *
   * @return The local id for this feature
   */
  public String getAndroidId() {
    return androidId;
  }

  /**
   * Retrieves the current (html) description of this feature. The description
   * is stored in the feature metadata.
   *
   * @return The description of this feature
   */
  public String getDescription() {
    return featureInfo.getDescription();
  }

  /**
   * Sets the description of this feature. That description is stored in the
   * feature metadata.
   *
   * @param description The new description of this feature
   */
  public synchronized void setDescription(String description) {
    featureInfo.setDescription(description);
  }

  /**
   * Retrieves the point at the given index for this feature.
   *
   * @param index The index of the point desired
   * @return A {@link GeoPoint} representing the point or null if that point
   *         doesn't exist
   */
  public GeoPoint getPoint(int index) {
    if (latitudeE6.size() <= index) {
      return null;
    }
    return new GeoPoint(latitudeE6.get(index), longitudeE6.get(index));
  }

  /**
   * Counts the number of points in this feature and return that count.
   *
   * @return The number of points in this feature
   */
  public int getPointCount() {
    return latitudeE6.size();
  }

  /**
   * Retrieves the title of this feature. That title is stored in the feature
   * metadata.
   *
   * @return the current title of this feature
   */
  public String getTitle() {
    return featureInfo.getTitle();
  }

  /**
   * Retrieves the type of this feature. That type is stored in the feature
   * metadata.
   *
   * @return One of {@link #MARKER}, {@link #LINE}, or {@link #SHAPE}
   *         identifying the type of this feature
   */
  public int getType() {
    return featureInfo.getType();
  }

  /**
   * Retrieves the current color of this feature as an ARGB color integer.
   * That color is stored in the feature metadata.
   *
   * @return The ARGB color of this feature
   */
  public int getColor() {
    return featureInfo.getColor();
  }

  /**
   * Retrieves the current line width of this feature. That line width is
   * stored in the feature metadata.
   *
   * @return The line width of this feature
   */
  public int getLineWidth() {
    return featureInfo.getLineWidth();
  }

  /**
   * Retrieves the current fill color of this feature as an ARGB color
   * integer. That color is stored in the feature metadata.
   *
   * @return The ARGB fill color of this feature
   */
  public int getFillColor() {
    return featureInfo.getFillColor();
  }

  /**
   * Retrieves the current icon url of this feature. That icon url is stored
   * in the feature metadata.
   *
   * @return The icon url for this feature
   */
  public String getIconUrl() {
    return featureInfo.getIconUrl();
  }

  /**
   * Sets the title of this feature. That title is stored in the feature
   * metadata.
   *
   * @param title The new title of this feature
   */
  public synchronized void setTitle(String title) {
    featureInfo.setTitle(title);
  }

  /**
   * Sets the type of this feature. That type is stored in the feature
   * metadata.
   *
   * @param type The new type of the feature. That type must be one of
   *        {@link #MARKER}, {@link #LINE}, or {@link #SHAPE}
   */
  public synchronized void setType(int type) {
    featureInfo.setType(type);
  }

  /**
   * Sets the ARGB color of this feature. That color is stored in the feature
   * metadata.
   *
   * @param color The new ARGB color of this feature
   */
  public synchronized void setColor(int color) {
    featureInfo.setColor(color);
  }

  /**
   * Sets the icon url of this feature. That icon url is stored in the feature
   * metadata.
   *
   * @param url The new icon url of the feature
   */
  public synchronized void setIconUrl(String url) {
    featureInfo.setIconUrl(url);
  }
}
