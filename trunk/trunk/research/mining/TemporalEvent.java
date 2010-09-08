package research.mining;


public abstract class TemporalEvent implements Comparable {
    //milisegundos desde 1 de enero de 1970 hasta el principio del evento
    private long absoluteBeginingTime; //L1
    //duracion del evento en milisegundos
    private long duration; //L1

    public enum DETAILLEVEL {
        LOW, MEDIUM, HIGH, EVERYTHING} ;

        public TemporalEvent() {

        }

        public TemporalEvent(long absoluteBeginingTime, long duration) {
            this.absoluteBeginingTime = absoluteBeginingTime;
            this.duration = duration;
        }

      //  public abstract String genrateDescriptors(DETAILLEVEL level);


        public long getAbsoluteBeginingTime() {
            return absoluteBeginingTime;
        }

        public long getDuration() {
            return duration;
        }

        public void setAbsoluteBeginingTime(long absoluteBeginingTime) {
            this.absoluteBeginingTime = absoluteBeginingTime;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        /**
         * Compares this object with the specified object for order.
         *
         * @param o the object to be compared.
         * @return a negative integer, zero, or a positive integer as this
         *   object is less than, equal to, or greater than the specified object.
         * @todo Implement this java.lang.Comparable method
         */
        public int compareTo(Object o) {
            TemporalEvent i = (TemporalEvent) o;
            if (i.getAbsoluteBeginingTime() < this.absoluteBeginingTime) {
                return 1;
            } else if (i.getAbsoluteBeginingTime() > this.absoluteBeginingTime) {
                return -1;
            }
            return 1;
        }

        public int hashCode() {
            int valor = (int) (this.duration | this.absoluteBeginingTime);
            return 37 * 17 + valor ^ (valor >>> 32);
        }

        public boolean equals(Object o) {
            if (!(o instanceof TemporalEvent)) {
                return false;
            }
            TemporalEvent t = (TemporalEvent) o;
            return t.absoluteBeginingTime == this.absoluteBeginingTime &&
                    t.duration == this.duration;
        }

    }
