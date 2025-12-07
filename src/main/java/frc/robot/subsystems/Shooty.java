package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Ground intake subsystem that uses a single motor to intake game pieces.
 */
public class Shooty extends SubsystemBase {
    private final TalonFX shootMotor;
    private final TalonFX hoodMotor;
    private final VoltageOut voltageRequest = new VoltageOut(0);

    // Intake speeds (in volts)
    private static final double SHOOT_SPEED = 8.0;  // Adjust this value as needed
    private static final double HOOD_CHANGE = -6.0; // For reversing if needed

    /**
     * Creates a new GroundIntake subsystem.
     *
     * @param motorID The CAN ID of the intake motor
     */
    public Shooty(int shootID, int hoodID) {

        shootMotor = new TalonFX(shootID);
        hoodMotor = new TalonFX(hoodID);

        // Configure the motor
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        config.CurrentLimits.SupplyCurrentLimit = 40.0;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;

        intakeMotor.getConfigurator().apply(config);
        
    }

    /**
     * Runs the intake motor to pull in game pieces.
     */
    public void shoot() {
        shootMotor.setControl(voltageRequest.withOutput(SHOOT_SPEED));
    }

    /**
     * Runs the intake motor in reverse to eject game pieces.
     */
    public void adjustHood() {
        hoodMotor.setControl(voltageRequest.withOutput(HOOD_CHANGE));
    }

    /**
     * Stops the intake motor.
     */
    public void stopShooting() {
        shootMotor.setControl(voltageRequest.withOutput(0));
    }

    public void stopShooting() {
        adjustHood
    }

    /**
     * Command to run the intake while the button is held.
     *
     * @return Command that runs the intake
     */
    public Command shootCommand() {
        return this.runEnd(
            this::shoot,
            this::stopShooting
        );
    }

    /**
     * Command to run the outtake while the button is held.
     *
     * @return Command that runs the outtake
     */
    public Command aimFurtherCommand() {
        return this.runEnd(
            this::shoot,
            this::stopShooting
        );
    }

    public Command aimCloserCommand() {
        return this.runEnd(
            this::shoot,
            this::stopTurning
        );
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
