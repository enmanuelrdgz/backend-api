package com.github.enma11235.generic.poll.system.dto.response;

import com.github.enma11235.generic.poll.system.dto.model.PollData;

public class AddVoteResponseBody {

    private PollData pollData;

    public AddVoteResponseBody(PollData pollData) {
        this.pollData = pollData;
    }

    public PollData getPollData() {
        return pollData;
    }

    public void setPollData(PollData pollData) {
        this.pollData = pollData;
    }
}
