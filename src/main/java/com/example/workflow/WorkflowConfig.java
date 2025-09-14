package com.example.workflow;

import io.smallrye.config.ConfigMapping;

import java.util.Map;

@ConfigMapping(prefix = "workflow")
public interface WorkflowConfig{
    Map<String, Step> steps();

    interface Step{
        Type type();
        Source source();
        Target target();
        //Map<String, Object> configuration();

        enum Type {
            TRANSFORM1,
            TRANSFORM2,
            TRANSFORM3
        }

        interface Source{
            Type type();
            String value();

            enum Type{
                PUBSUB,
                IN_MEMORY
            }
        }

        interface Target{
            Type type();
            String value();

            enum Type{
                PUBSUB,
                IN_MEMORY
            }
        }
    }


}
