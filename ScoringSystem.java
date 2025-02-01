public ScoringSystem(HardwareMap hardwareMap) {
            worm = hardwareMap.get(DcMotor.class, "worm");
            scoreSlide = hardwareMap.get(DcMotor.class, "slide3");
            claw = hardwareMap.get(Servo.class, "servo3");
            clawTwo = hardwareMap.get(Servo.class, "servo2");
            clawThree = hardwareMap.get(Servo.class, "servo1");

            // Configure motors with brake behavior
            worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            scoreSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            worm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            scoreSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            worm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            scoreSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        public class LiftToScore implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    worm.setTargetPosition(WORM_SCORE_POSITION);
                    scoreSlide.setTargetPosition(SLIDE_SCORE_POSITION);
                    worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    scoreSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    worm.setPower(WORM_UP_POWER);
                    scoreSlide.setPower(SLIDE_UP_POWER);

                    clawTwo.setPosition(CLAW_TWO_SCORE);
                    clawThree.setPosition(CLAW_THREE_SCORE);

                    initialized = true;
                }

                int currentWormPos = worm.getCurrentPosition();
                int currentSlidePos = scoreSlide.getCurrentPosition();

                packet.put("wormPos", currentWormPos);
                packet.put("slidePos", currentSlidePos);
                packet.put("wormTarget", WORM_SCORE_POSITION);
                packet.put("slideTarget", SLIDE_SCORE_POSITION);

                boolean wormAtTarget = Math.abs(currentWormPos - WORM_SCORE_POSITION) < POSITION_TOLERANCE;
                boolean slideAtTarget = Math.abs(currentSlidePos - SLIDE_SCORE_POSITION) < POSITION_TOLERANCE;

                return !(wormAtTarget && slideAtTarget);
            }
        }
        public class LiftToScoredos implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    worm.setTargetPosition(WORM_SCORE_POSITION);
                    scoreSlide.setTargetPosition(SLIDE_SCORE_POSITION);
                    worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    scoreSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    worm.setPower(WORM_UP_POWER);
                    scoreSlide.setPower(SLIDE_UP_POWER);

                    clawTwo.setPosition(.355);
                    clawThree.setPosition(CLAW_THREE_SCORE);

                    initialized = true;
                }

                int currentWormPos = worm.getCurrentPosition();
                int currentSlidePos = scoreSlide.getCurrentPosition();

                packet.put("wormPos", currentWormPos);
                packet.put("slidePos", currentSlidePos);
                packet.put("wormTarget", WORM_SCORE_POSITION);
                packet.put("slideTarget", SLIDE_SCORE_POSITION);

                boolean wormAtTarget = Math.abs(currentWormPos - WORM_SCORE_POSITION) < POSITION_TOLERANCE;
                boolean slideAtTarget = Math.abs(currentSlidePos - SLIDE_SCORE_POSITION) < POSITION_TOLERANCE;

                return !(wormAtTarget && slideAtTarget);
            }
        }

        public class ReturnToZero implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    worm.setTargetPosition(0);
                    scoreSlide.setTargetPosition(0);
                    worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    scoreSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    worm.setPower(WORM_DOWN_POWER);
                    scoreSlide.setPower(SLIDE_DOWN_POWER);
                    initialized = true;
                }

                int currentWormPos = worm.getCurrentPosition();
                int currentSlidePos = scoreSlide.getCurrentPosition();

                packet.put("wormPos", currentWormPos);
                packet.put("slidePos", currentSlidePos);

                boolean wormAtTarget = Math.abs(currentWormPos) < POSITION_TOLERANCE;
                boolean slideAtTarget = Math.abs(currentSlidePos) < POSITION_TOLERANCE;

                return !(wormAtTarget && slideAtTarget);
            }
        }

        public class MoveToIntermediate implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    worm.setTargetPosition(WORM_INTERMEDIATE);

                    scoreSlide.setTargetPosition(SLIDE_INTERMEDIATE);
                    worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    scoreSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    worm.setPower(WORM_UP_POWER);
                    scoreSlide.setPower(SLIDE_UP_POWER);
                    initialized = true;
                }

                int currentWormPos = worm.getCurrentPosition();
                int currentSlidePos = scoreSlide.getCurrentPosition();

                packet.put("wormPos", currentWormPos);
                packet.put("slidePos", currentSlidePos);

                boolean wormAtTarget = Math.abs(currentWormPos - WORM_INTERMEDIATE) < POSITION_TOLERANCE;
                boolean slideAtTarget = Math.abs(currentSlidePos - SLIDE_INTERMEDIATE) < POSITION_TOLERANCE;

                return !(wormAtTarget && slideAtTarget);
            }
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(CLAW_CLOSED);
                sleep(500);
                return false;
            }
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(CLAW_OPEN);
                return false;
            }
        }
        public class ClawThreeMoveBack implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                sleep(300);
                clawThree.setPosition(.955);
                return false;
            }
        }

        public Action liftToScore() { return new LiftToScore(); }
        public Action liftToScoredos() { return new LiftToScoredos(); }

        public Action returnToZero() { return new ReturnToZero(); }
        public Action closeClaw() { return new CloseClaw(); }
        public Action openClaw() { return new OpenClaw(); }
        public Action openClawthree() { return new ClawThreeMoveBack(); }


        public Action moveToIntermediate() { return new MoveToIntermediate(); }
    }
