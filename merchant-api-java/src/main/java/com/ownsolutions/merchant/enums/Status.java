package com.ownsolutions.merchant.enums;

public enum Status {

    READY (1){
        @Override
        public boolean isReady() {
            return true;
        }
    },
    FAILED (2){
        @Override
        public boolean isFailed() {
            return true;
        }
    },
    USED (3) {
        @Override
        public boolean isUsed() {
            return true;
        }
    },
    EXPIRED (4) {
        @Override
        public boolean isExpired() {
            return true;
        }
    },
    INVALIDATED (5) {
        @Override
        public boolean isInvalidated() {
            return true;
        }
    },
    REVOKED (6) {
        @Override
        public boolean isRevoked() {
            return true;
        }
    },
    PENDING (7) {
        @Override
        public boolean isPending() {
            return true;
        }
    };

    private int statusCode;

    public boolean isReady() {return false;}
    public boolean isFailed() {return false;}
    public boolean isUsed() {return false;}
    public boolean isExpired() {return false;}
    public boolean isInvalidated() {return false;}
    public boolean isRevoked() {return false;}
    public boolean isPending() {return false;}

    Status (int statusCode) {
        this.statusCode = statusCode;
    }
}