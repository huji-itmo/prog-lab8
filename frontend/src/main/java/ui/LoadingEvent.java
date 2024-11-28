package ui;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadingEvent extends Event {

    public boolean doneLoading;

    public static final EventType<LoadingEvent> LOADING_EVENT_EVENT_TYPE =
            new EventType<>(Event.ANY, "LOADING_EVENT_EVENT_TYPE");

    public LoadingEvent(Object source, EventTarget eventTarget, boolean isLoading) {
        super(source, eventTarget, LOADING_EVENT_EVENT_TYPE);

        doneLoading = isLoading;
    }

    public LoadingEvent(boolean isLoading) {
        super(LOADING_EVENT_EVENT_TYPE);

        doneLoading = isLoading;
    }

    public LoadingEvent copyFor(Object newSource, EventTarget newTarget) {
        return (LoadingEvent) super.copyFor(newSource, newTarget);
    }
}
