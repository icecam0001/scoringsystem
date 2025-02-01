# FTC Robot Scoring System

This repository contains the scoring system implementation for our FTC robot. The system controls a worm drive and scoring slide mechanism with multiple servo-controlled claws for precise pixel manipulation and scoring.

## System Components

### Hardware Components
- Worm drive motor (DC)
- Scoring slide motor (DC)
- Three servo-controlled claws:
  - Main claw (servo3)
  - Secondary claw (servo2)
  - Tertiary claw (servo1)

### Key Features
- Encoder-based position control for precise movements
- Multiple preset positions (scoring, intermediate, zero)
- Brake behavior configuration for stability
- Telemetry packet integration for real-time position monitoring
- Position tolerance checking for accurate movements

## Action Classes

### LiftToScore
Primary scoring position action that:
- Moves worm and slide to scoring positions
- Configures servo positions for scoring
- Monitors position accuracy using encoders
- Reports telemetry data during operation

### LiftToScoredos
Alternative scoring position with different servo configurations

### ReturnToZero
Returns mechanism to home position:
- Resets both worm and slide to zero position
- Uses different power settings for downward movement
- Includes position verification

### MoveToIntermediate
Moves to intermediate position for transition movements:
- Custom worm and slide positions
- Useful for avoiding obstacles or preparing for scoring

### Claw Control Actions
- `CloseClaw`: Closes main claw with delay
- `OpenClaw`: Opens main claw
- `ClawThreeMoveBack`: Controls tertiary claw movement with delay

## Usage

Initialize the scoring system with your robot's hardware map:

```java
ScoringSystem scoringSystem = new ScoringSystem(hardwareMap);
```

Execute actions using the provided methods:

```java
scoringSystem.liftToScore()      // Move to scoring position
scoringSystem.returnToZero()     // Return to home position
scoringSystem.moveToIntermediate() // Move to intermediate position
scoringSystem.closeClaw()        // Control main claw
```

## Implementation Details

- Uses FTC SDK's `Action` interface for movement control
- Implements position tolerance checking for accuracy
- Includes telemetry reporting for debugging and monitoring
- Configures motors with brake behavior for stability
- Uses encoder-based position control for reliable movements

## Setup Requirements

1. Configure motors in your robot configuration:
   - "worm" - Worm drive motor
   - "slide3" - Scoring slide motor
   - "servo1" - Tertiary claw
   - "servo2" - Secondary claw
   - "servo3" - Main claw

2. Ensure proper encoder connections and calibration

## Integration

This scoring system is designed to be part of a larger robot control system. It integrates with:
- FTC SDK
- Robot hardware map
- Telemetry system for debugging and monitoring

## Notes

- All position constants (WORM_SCORE_POSITION, SLIDE_SCORE_POSITION, etc.) should be calibrated for your specific robot setup
- Position tolerance can be adjusted based on your accuracy requirements
- Power settings for motors can be tuned for optimal performance
