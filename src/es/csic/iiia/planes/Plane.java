/*
 * Software License Agreement (BSD License)
 *
 * Copyright 2012 Marc Pujol <mpujol@iiia.csic.es>.
 *
 * Redistribution and use of this software in source and binary forms, with or
 * without modification, are permitted provided that the following conditions
 * are met:
 *
 *   Redistributions of source code must retain the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer.
 *
 *   Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer in the documentation and/or other
 *   materials provided with the distribution.
 *
 *   Neither the name of IIIA-CSIC, Artificial Intelligence Research Institute
 *   nor the names of its contributors may be used to
 *   endorse or promote products derived from this
 *   software without specific prior written permission of
 *   IIIA-CSIC, Artificial Intelligence Research Institute
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package es.csic.iiia.planes;

import es.csic.iiia.planes.idle.IdleStrategy;
import es.csic.iiia.planes.definition.DPlane;
import es.csic.iiia.planes.evaluation.EvaluationStrategy;
import es.csic.iiia.planes.gui.Drawable;
import es.csic.iiia.planes.messaging.MessagingAgent;
import java.awt.Color;
import java.util.List;

/**
 * Represents an Unmannered Aerial Vehicle (UAV).
 *
 * @author Marc Pujol <mpujol@iiia.csic.es>
 */
public interface Plane extends MessagingAgent {

    /**
     * Plane states
     */
    public enum State {
        NORMAL, TO_CHARGE, CHARGING
    }

    /**
     * Gets the plane's id.
     *
     * @return the id.
     */
    public int getId();

    /**
     * Adds a new task to the list of tasks owned by this plane
     *
     * Task addition triggers a reevaluation of the next task to be completed.
     *
     * @param task to add
     */
    public void addTask(Task task);

    /**
     * Removes a task from the list of tasks owned by this plane.
     *
     * Task removal triggers a reevaluation of the next task to be completed.
     *
     * @param task that has been removed
     */
    public Task removeTask(Task task);

    /**
     * Gets the list of tasks assigned to this plane.
     *
     * @return list of tasks.
     */
    List<Task> getTasks();

    /**
     * Set plane's the remaining battery, in tenths of second.
     *
     * @param battery
     */
    void setBattery(long battery);

    /**
     * Get the plane's remianing battery in tenths of second.
     *
     * @return the reamining battery of this plane (in flight tenths of second)
     */
    long getBattery();

    /**
     * Set the battery capacity (maximum battery charge in tenths of second)
     *
     * @param capacity
     */
    public void setBatteryCapacity(long capacity);

    /**
     * Get the battery capacity (maximum battery charge in tenths of second)
     *
     * @return the capacity of the battery of this plane.
     */
    public long getBatteryCapacity();

    /**
     * Get the evaluation strategy used by this plane.
     *
     * @return the evaluation strategy used by this plane.
     */
    public EvaluationStrategy getEvaluationStrategy();

    /**
     * Set the evaluation strategy used by this plane.
     *
     * @param eval new evaluation strategy used by this plane.
     */
    public void setEvaluationStrategy(EvaluationStrategy eval);

    /**
     * Get the estimated cost of performing <em>task</em>, according to the
     * plane's {@link EvaluationStrategy}.
     *
     * @see #getEvaluationStrategy()
     * @see #setEvaluationStrategy()
     *
     * @param task task to evaluate.
     * @return cost of performing the given task.
     */
    public double getCost(Task task);

    /**
     * Set the idle strategy of this plane.
     * <p/>
     * This strategy defines the behavior of the plane whenever it has no tasks
     * to accomplish.
     *
     * @param strategy new strategy of this plane.
     */
    public void setIdleStrategy(IdleStrategy strategy);

    /**
     * Get the idle strategy of this plane.
     */
    public IdleStrategy getIdleStrategy();



    /***************************************************************************
     * MOVEMENT STUFF. This should only be used by idling strategies!
     **************************************************************************/

    /**
     * Sets the plane's destination.
     *
     * @param location location where to move towards.
     */
    public void setDestination(Location location);

    /**
     * Moves the plane towards its current destination.
     * <p/>
     * <em>Warning:</em> this action should never be executed twice during a
     * single iteration.
     *
     * @return True if the destination has been reached, or False otherwise.
     */
    public boolean move();

    /***************************************************************************
     * STATISTICS TRACKING
     **************************************************************************/

    /**
     * Get the total flight distance of this plane, for evaluation purposes.
     *
     * @return the total flight distance of this plane.
     */
    public double getTotalDistance();



    /***************************************************************************
     * STUFF RELATED TO DRAWING
     **************************************************************************/

    /**
     * Number of history points to store, for debugging and displaying
     * reasons.
     */
    public static int NUM_COMPLETED_TASKS = 20;

    /**
     * Set the plane's current angle
     *
     * @param angle the angle.
     */
    public void setAngle(double angle);

    /**
     * Get the plane's current angle
     *
     * @return the angle.
     */
    public double getAngle();

    /**
     * Get the completed locations.
     */
    public List<Location> getCompletedLocations();

    /**
     * Get the planned locations.
     */
    public List<Location> getPlannedLocations();

    /**
     * Get the next planned task.
     */
    public Task getNextTask();

    /**
     * Get the drawer for this plane.
     */
    public Drawable getDrawer();

    /**
     * Get the plane's color.
     */
    public Color getColor();

    /**
     * Set the plane's color, used when drawing the GUI.
     *
     * The color must be specified as an int array of length 3, representing
     * the RGB color values in the range 0-255.
     *
     * @see DPlane#getColor()
     * @param color
     */
    public void setColor(int[] color);

}