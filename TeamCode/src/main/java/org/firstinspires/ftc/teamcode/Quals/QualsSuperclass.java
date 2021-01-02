package org.firstinspires.ftc.teamcode.Quals;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Vision;
import org.firstinspires.ftc.teamcode.Subsystems.WobbleMech;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Util.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Bitmap.createBitmap;
import static android.graphics.Bitmap.createScaledBitmap;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.teamcode.Util.Constants.motorTicksPerRev;

public abstract class QualsSuperclass extends LinearOpMode {

    // ROBOT OBJECTS -------------------------------------------------------------------------------

    public int initCurrent = 0;
    public final int initTotal = 4;

    // Constants
    public Constants constants = new Constants();

    // Drivetrain
    public Drivetrain drivetrain = new Drivetrain();

    // Wobble Mech
    // public WobbleMech wobbleMech = new WobbleMech();

    // Shooter
    // public Shooter shooter = new Shooter();

    // Vision
    public Vision vision = new Vision();

    // METHODS -------------------------------------------------------------------------------------

    // Robot Initialization
    public void initialize() {

        // Telemetry
        telemetry.setAutoClear(false);
        telemetry.addLine("Initializing Robot...");
        telemetry.update();
        sleep(500);

        // Device Initialization

        // Vision
        vision.webcamName = hardwareMap.get(WebcamName.class, "Webcam");
        vision.initialize();
        initCurrent++;

        telemetry.addLine("Vision initialized" + "(" + initCurrent + "/" + initTotal + ")");
        telemetry.update();
        sleep(500);

        /*
        // Drivetrain
        drivetrain.frontLeft = (DcMotorEx)hardwareMap.dcMotor.get("frontLeft");
        drivetrain.frontRight = (DcMotorEx)hardwareMap.dcMotor.get("frontRight");
        drivetrain.backLeft = (DcMotorEx)hardwareMap.dcMotor.get("backLeft");
        drivetrain.backRight = (DcMotorEx)hardwareMap.dcMotor.get("backRight");

        drivetrain.imu = hardwareMap.get(BNO055IMU.class, "imu");
        drivetrain.leftEncoder = hardwareMap.dcMotor.get("leftEncoder");
        drivetrain.rightEncoder = hardwareMap.dcMotor.get("rightEncoder");
        drivetrain.horzEncoder = hardwareMap.dcMotor.get("horzEncoder");

        drivetrain.initialize();
        initCurrent++;
         */

        telemetry.addLine("Drivetrain initialized" + "(" + initCurrent + "/" + initTotal + ")");
        telemetry.update();
        sleep(500);

        /*
        // Shooter
        shooter.mShooter = (DcMotorEx)hardwareMap.dcMotor.get("mShooter");
        shooter.sTrigger = hardwareMap.servo.get("sTrigger");
        shooter.initialize();
        initCurrent++;
         */

        telemetry.addLine("Shooter initialized" + "(" + initCurrent + "/" + initTotal + ")");
        telemetry.update();
        sleep(500);

        /*
        // Wobble Mech
        wobbleMech.arm = (DcMotorEx)hardwareMap.dcMotor.get("arm");
        wobbleMech.lClaw = hardwareMap.servo.get("lClaw");
        wobbleMech.rClaw = hardwareMap.servo.get("rClaw");
        wobbleMech.initialize();
        initCurrent++;
         */

        telemetry.addLine("Wobble Mech initialized" + "(" + initCurrent + "/" + initTotal + ")");
        telemetry.update();
        sleep(500);

        // Telemetry
        telemetry.addLine("Initialization Finished" + "(" + initCurrent + "/" + initTotal + ")");
        telemetry.update();
        sleep(500);

        // telemetry.addLine("Load wobble goal and press 'Start'...");
        // telemetry.update();

        /*
        // Wait for controller input for wobble goal pre-load
        while (wobbleMech.initK == 0) {

            if (gamepad1.a && constants.a == 0) {
                constants.a = 1;
            } else if (!gamepad1.a && constants.a == 1) {

                // Set wobble goal to pre-loaded position
                wobbleMech.clawClose();
                sleep(2000);
                wobbleMech.setArmPosition(0, 0.2);

                telemetry.addLine("Wobble goal loaded");
                telemetry.update();

                constants.a = 0;
                wobbleMech.initK = 1;
                sleep(500);
            }

            // Cancel wobble goal pre-load
            if (gamepad1.b && constants.b == 0) {
                constants.b = 1;

            } else if (!gamepad1.b && constants.b == 1) {

                // Reset wobble mech
                resetWobbleMech();

                telemetry.addLine("Wobble goal not loaded");
                telemetry.update();

                constants.b = 0;
                wobbleMech.initK = 2;
                sleep(500);
            }

            // Break out of loop if initialization is stopped to prevent forced restart
            if (isStopRequested()) {
                break;
            }
        }
         */

        // Display robot rotation
        telemetry.setAutoClear(true);
        while(!isStarted()) {
            telemetry.addData("FC Heading (Deg)", Math.toDegrees(drivetrain.getHeading(true)));
            telemetry.addData("Heading (Deg)", drivetrain.getHeading(false));
            telemetry.update();
        }
    }

    // Drive Methods

    /*

    public void forward(double pow, double inches) {

        double target = inches * drivetrain.DRIVE_TICKS_PER_INCH;

        if (opModeIsActive()) {

            drivetrain.resetDriveEncoders();
            drivetrain.setDriveTarget(target,
                    1, 1,
                    1, 1);
            drivetrain.setDriveMode();

            while (opModeIsActive() && drivetrain.driveIsBusy()) {
                drivetrain.setDrivePower(pow);
            }

            drivetrain.setDrivePower(0);
            drivetrain.resetDriveMode();
        }
    }

    public void backward(double pow, double inches) {

        double target = inches * drivetrain.DRIVE_TICKS_PER_INCH;

        if (opModeIsActive()) {

            drivetrain.resetDriveEncoders();
            drivetrain.setDriveTarget(target,
                    -1, -1,
                    -1, -1);
            drivetrain.setDriveMode();

            while (opModeIsActive() && drivetrain.driveIsBusy()) {
                drivetrain.setDrivePower(pow);
            }

            drivetrain.setDrivePower(0);
            drivetrain.resetDriveMode();
        }
    }

    public void strafeRight(double pow, double inches) {

        double target = inches * drivetrain.DRIVE_TICKS_PER_INCH;
        target *= drivetrain.DRIVE_STRAFE_CORRECTION;

        if (opModeIsActive()) {

            drivetrain.resetDriveEncoders();
            drivetrain.setDriveTarget(target,
                    1, -1,
                    -1, 1);
            drivetrain.setDriveMode();

            while (opModeIsActive() && drivetrain.driveIsBusy()) {
                drivetrain.setDrivePower(pow);
            }

            drivetrain.setDrivePower(0);
            drivetrain.resetDriveMode();
        }
    }

    public void strafeLeft(double pow, double inches) {

        double target = inches * drivetrain.DRIVE_TICKS_PER_INCH;
        target *= drivetrain.DRIVE_STRAFE_CORRECTION;

        if (opModeIsActive()) {

            drivetrain.resetDriveEncoders();
            drivetrain.setDriveTarget(target,
                    -1, 1,
                    1, -1);
            drivetrain.setDriveMode();

            while (opModeIsActive() && drivetrain.driveIsBusy()) {
                drivetrain.setDrivePower(pow);
            }

            drivetrain.setDrivePower(0);
            drivetrain.resetDriveMode();
        }
    }

    public void rotateRight(double pow, double angle) {

        double target = angle * drivetrain.DRIVE_TICKS_PER_DEGREE;

        if (opModeIsActive()) {

            drivetrain.resetDriveEncoders();
            drivetrain.setDriveTarget(target,
                    1, -1,
                    1, -1);
            drivetrain.setDriveMode();

            while (opModeIsActive() && drivetrain.driveIsBusy()) {
                drivetrain.setDrivePower(pow);
            }

            drivetrain.setDrivePower(0);
            drivetrain.resetDriveMode();
        }
    }

    public void rotateLeft(double pow, double angle) {

        double target = angle * drivetrain.DRIVE_TICKS_PER_DEGREE;

        if (opModeIsActive()) {

            drivetrain.resetDriveEncoders();
            drivetrain.setDriveTarget(target,
                    -1, 1,
                    -1, 1);
            drivetrain.setDriveMode();

            while (opModeIsActive() && drivetrain.driveIsBusy()) {
                drivetrain.setDrivePower(pow);
            }

            drivetrain.setDrivePower(0);
            drivetrain.resetDriveMode();
        }
    }

    public void drive(boolean isFieldCentric) {

        // FIELD-CENTRIC DRIVE -----------------------------------------------------------------

        double forward = -gamepad1.left_stick_y;
        double right = gamepad1.left_stick_x;
        double clockwise = gamepad1.right_stick_x;

        // Joystick deadzones
        if (Math.abs(forward) < 0.05)
            forward = 0;
        if (Math.abs(right) < 0.05)
            right = 0;
        if (Math.abs(clockwise) < 0.05)
            clockwise = 0;

        if (isFieldCentric) {

            // Math
            if (drivetrain.getHeading(true) < 0) {       // If theta is measured clockwise from zero reference

                drivetrain.temp = forward * Math.cos(drivetrain.getHeading(true)) + right * Math.sin(-drivetrain.getHeading(true));
                right = -forward * Math.sin(-drivetrain.getHeading(true)) + right * Math.cos(drivetrain.getHeading(true));
                forward = drivetrain.temp;
            }

            if (drivetrain.getHeading(true) >= 0) {    // If theta is measured counterclockwise from zero reference

                drivetrain.temp = forward * Math.cos(drivetrain.getHeading(true)) - right * Math.sin(drivetrain.getHeading(true));
                right = forward * Math.sin(drivetrain.getHeading(true)) + right * Math.cos(drivetrain.getHeading(true));
                forward = drivetrain.temp;
            }
        }

        // Assign calculated values to the power variables
        drivetrain.flpower = forward + right + clockwise;
        drivetrain.frpower = forward - right - clockwise;
        drivetrain.blpower = forward - right + clockwise;
        drivetrain.brpower = forward + right - clockwise;

        // if you have the testing time, maybe remove this one day and see if it causes any
        // problems?
        // Find the maximum of the powers
        double max = Math.max(  Math.max(Math.abs(drivetrain.flpower), Math.abs(drivetrain.frpower)),
                Math.max(Math.abs(drivetrain.blpower), Math.abs(drivetrain.brpower))  );
        // Use this to make sure no motor powers are above 1 (the max value the motor can accept)
        if (max > 1) {

            drivetrain.flpower /= max;
            drivetrain.frpower /= max;
            drivetrain.blpower /= max;
            drivetrain.brpower /= max;
        }

        // Motor powers are set to the power of 3 so that the drivetrain motors accelerates
        // exponentially instead of linearly
        // Note: you may consider, in the future, moving this code block to before the
        // max > 1 code block to see if that is better or worse performance, but I think
        // it will be worse because it may mess up proportions
        drivetrain.flpower = Math.pow(drivetrain.flpower, 3);
        drivetrain.blpower = Math.pow(drivetrain.blpower, 3);
        drivetrain.frpower = Math.pow(drivetrain.frpower, 3);
        drivetrain.brpower = Math.pow(drivetrain.brpower, 3);

        // Motor Power is decreased while the right trigger is held down to allow for more
        // precise robot control
        if (gamepad1.right_trigger > 0.8) {

            drivetrain.flpower /= 3;
            drivetrain.frpower /= 3;
            drivetrain.blpower /= 3;
            drivetrain.brpower /= 3;
        }

        // If the trigger is held down, but not pressed all the way down, motor power will
        // slow down proportionally to how much the trigger is pressed
        else if (gamepad1.right_trigger > 0.1) {

            double driveSlow = -0.8 * gamepad1.right_trigger + 1;

            drivetrain.flpower *= driveSlow;
            drivetrain.frpower *= driveSlow;
            drivetrain.blpower *= driveSlow;
            drivetrain.brpower *= driveSlow;
        }

            // Alternate version of drive slowing
            // This version does not scale proportionally to the press, but uses a constant
            // multiplier instead
            // Programmers may choose to use this version of drive slowing instead due to a
            // driver's preference
            // if (gamepad1.right_trigger > 0.5){

                // flpower /= 3;
                // blpower /= 3;
                // frpower /= 3;
                // brpower /= 3;
            // }

        drivetrain.setPowerAll(drivetrain.flpower, drivetrain.frpower, drivetrain.blpower, drivetrain.brpower);

        //odo and print info
        drivetrain.updatePosition(telemetry);
    }

     */

    // Vision Methods

    public void vuforiaScanStack(boolean saveBitmaps) {

        // Capture frame from camera
        vision.captureFrame();
        telemetry.addLine("Frame captured");
        telemetry.update();

        if (vision.rgbImage != null) {

            // Transpose frame into bitmaps
            vision.setBitmaps();
            telemetry.addLine("Frame converted to Bitmaps");
            telemetry.update();

            // Save bitmaps to .png files
            if (saveBitmaps) {
                vision.saveBitmap("Bitmap", vision.bitmap);
                vision.saveBitmap("CroppedBitmap", vision.croppedBitmap);
                telemetry.addLine("Frame converted to Bitmaps");
                telemetry.update();
            }

            // Scan bitmap for starter stack height
            scanBitmap();
            telemetry.addLine("Bitmaps scanned");
            telemetry.update();

            telemetry.addLine();
            telemetry.addData("Stack Height", vision.getStackHeight());
            telemetry.update();
        }
    }

    public void scanBitmap() {

        int[] yPos = {vision.croppedBitmap.getHeight()-vision.ringHeight/2,vision.ringHeight/2}; // Bottom to Top
        int[] xPos = {(vision.croppedBitmap.getWidth()/2)-20,vision.croppedBitmap.getWidth()/2,(vision.croppedBitmap.getWidth()/2)+20}; // Left to Right
        int pixel, r, g, b;

        for (int i = 0; i < yPos.length; i++) {

            for (int j = 0; j < xPos.length; j++) {

                pixel = vision.croppedBitmap.getPixel(xPos[j],yPos[i]);
                r = Color.red(pixel);
                g = Color.green(pixel);
                b = Color.blue(pixel);

                if ((17.5/100.0)*(r+g) > b) {
                    vision.check++;
                }
            }

            if (vision.check >= 2) {
                vision.ringsDetected++;
            }

            vision.check = 0;
        }
    }

    public void vuforiaScanTrackable() {

        // Note: To use the remote camera preview:
        // AFTER you hit Init on the Driver Station, use the "options menu" to select "Camera Stream"
        // Tap the preview window to receive a fresh image.

        vision.targetsUltimateGoal.activate();

        // Change condition to something else later
        while (!isStopRequested()) {

            // check all the trackable targets to see which one (if any) is visible.
            vision.targetVisible = false;
            for (VuforiaTrackable trackable : vision.allTrackables) {
                if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    vision.targetVisible = true;
                    break;
                }
            }
            telemetry.update();
        }

        // Disable Tracking when we are done;
        vision.targetsUltimateGoal.deactivate();
    }

    /*

    // Wobble Mech Methods

    public void aim() {
        wobbleMech.clawOpen();
        sleep(750);
        wobbleMech.setArmPosition(1, 0.4);
    }

    public void collect() {
        wobbleMech.clawClose();
        sleep(750);
        wobbleMech.setArmPosition(0, 0.2);
    }

    public void release() {
        wobbleMech.setArmPosition(1, 0.2);
        sleep(750);
        wobbleMech.clawOpen();
        sleep(750);
        resetWobbleMech();
    }

    public void drop() {
        wobbleMech.setArmPosition(2, 0.2);
        sleep(750);
        wobbleMech.clawOpen();
        sleep(750);
        resetWobbleMech();
    }

    public void resetWobbleMech() {
        wobbleMech.setArmPosition(0, 0.4);
        wobbleMech.clawClose();
    }

    public void zeroWobbleMech(){
        wobbleMech.setArmPosition(3, 0.4);
        wobbleMech.clawClose();
    }

     */
}