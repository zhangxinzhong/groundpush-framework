package com.groundpush.service;

import com.groundpush.core.model.Label;

import java.util.List;

public interface LabelService {

    List<Label> getLabelByType(Integer type);
}
