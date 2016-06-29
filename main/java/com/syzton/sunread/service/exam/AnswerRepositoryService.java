package com.syzton.sunread.service.exam;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.repository.exam.AnswerRepository;
@Service
public class AnswerRepositoryService implements AnswerService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AnswerRepositoryService.class);

	private AnswerRepository<Answer,Long> repository;



	@Autowired
	public AnswerRepositoryService(AnswerRepository<Answer,Long> repository) {
		this.repository = repository;
	}

	@Transactional
	@Override
	public Answer add(Answer added) {
		LOGGER.debug("Adding a new Answer entry with information: {}", added);
		return repository.save(added);
	}

	@Transactional(rollbackFor = { AnswerNotFoundException.class })
	@Override
	public Answer deleteById(Long id) throws AnswerNotFoundException {
		LOGGER.debug("Deleting a to-do entry with id: {}", id);

		Answer deleted = findById(id);
		LOGGER.debug("Deleting to-do entry: {}", deleted);

		repository.delete(deleted);
		return deleted;
	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Page<Answer> findAll(Pageable pageable) {
		Page<Answer> answerPages = repository.findAll(pageable);

		return answerPages;
	}
	@Transactional(readOnly = true, rollbackFor = { AnswerNotFoundException.class })
	@Override
	public Answer findById(Long id) throws AnswerNotFoundException {
		LOGGER.debug("Finding a to-do entry with id: {}", id);

		Answer found = repository.findOne(id);
		LOGGER.debug("Found to-do entry: {}", found);

		if (found == null) {
			throw new AnswerNotFoundException("No to-entry found with id: " + id);
		}

		return found;
	}

}
