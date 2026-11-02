// BITS ID: 2024SL70023 - CI Automated Build Trigger
package com.bits.hr;

public class LeaveCalculator {

    
    public int calculateRemainingLeave(int totalLeave, int leavesTaken) {
        if (totalLeave < 0 || leavesTaken < 0) {
            throw new IllegalArgumentException("Leave count cannot be negative");
        }
        if (leavesTaken > totalLeave) {
            throw new IllegalArgumentException("Leaves taken cannot exceed total leaves");
        }
        return totalLeave - leavesTaken;
    }

    
    public double calculateEncashment(int leaveBalance, double dailySalaryRate) {
        if (leaveBalance < 0 || dailySalaryRate < 0.0) {
            throw new IllegalArgumentException("Balance and daily rate must be non-negative");
        }
        return leaveBalance * dailySalaryRate;
    }
}
