public class NBody {
    /* This code inputs 2 arguments from the user, the first being how long you
    want the code to run for (the stopping time), and the second being the time
    interval. From this input, the code outputs an animated simulation of the
    universe with their respective velocities and accelerations, with music
    in the background.
     */
    public static void main(String[] args) {
        // Step 1. Parse command-line arguments.
        double tao = Double.parseDouble(args[0]); // Stopping time
        double dt = Double.parseDouble(args[1]);  // Time interval

        // Step 2. Read the universe from standard input.
        int n = StdIn.readInt();
        double radius = StdIn.readDouble();

        double[] px = new double[n]; // Array for storing planet x-positions
        double[] py = new double[n]; // Array for storing planet y-positions
        double[] vx = new double[n]; // Array for storing planet x-velocities
        double[] vy = new double[n]; // Array for storing planet y-velocities
        double[] mass = new double[n]; // Array for storing planet masses
        String[] image = new String[n]; // Array for storing planet images

        // For loop to input numbers into respective array
        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }

        // Step 3. Initialize standard drawing.
        StdDraw.setXscale(-radius, +radius);
        StdDraw.setYscale(-radius, +radius);
        StdDraw.enableDoubleBuffering();

        // Step 4. Play music on standard audio.
        StdAudio.playInBackground("2001.wav");

        // Step 5. Simulate the universe (main time loop).
        for (double j = 0; j < tao; j += dt) {

            // Step 5A. Calculate net forces.
            double[] fx = new double[n]; // Array to store all net-forces x
            double[] fy = new double[n]; // Array to store all net-forces y

            // Calculates net force between two different planets
            for (int i = 0; i < n; i++) {
                for (int k = 0; k < n; k++) {
                    if (i != k) {
                        double distance = Math.sqrt(
                                (px[i] - px[k]) * (px[i] - px[k])
                                        + (py[i] - py[k]) * (py[i]
                                        - py[k]));
                        double force = (6.67e-11 * mass[i] * mass[k])
                                / ((distance) * (distance));
                        double netforcex = force * ((px[k] - px[i]) / distance);
                        double netforcey = force * ((py[k] - py[i]) / distance);
                        fx[i] += netforcex;
                        fy[i] += netforcey;
                    }


                }
            }


            // Step 5B. Update velocities and positions.
            for (int i = 0; i < n; i++) {
                double ax = fx[i] / mass[i];
                double ay = fy[i] / mass[i];
                vx[i] = vx[i] + ax * dt;
                vy[i] = vy[i] + ay * dt;
                px[i] = px[i] + vx[i] * dt;
                py[i] = py[i] + vy[i] * dt;
            }

            // Step 5C. Draw universe to standard drawing.
            StdDraw.picture(0, 0, "starfield.jpg");
            // Draw planets
            for (int i = 0; i < n; i++) {
                StdDraw.picture(px[i], py[i], image[i]);
            }
            StdDraw.show();
            StdDraw.pause(20);

        }

        // Step 6. Print universe to standard output.
        StdOut.printf("%d\n", n);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < n; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], image[i]);
        }
    }
}
