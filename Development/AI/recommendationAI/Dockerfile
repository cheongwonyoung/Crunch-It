# 1. Use a Miniconda base image
FROM continuumio/miniconda3

# 2. Set working directory
WORKDIR /app

# 3. Copy the environment.yml file to the working directory
COPY environment.yml .

# 4. Create the Conda environment based on the environment.yml file
RUN conda env create -f environment.yml && \
    conda info --envs  # Check the created environments

# 5. Activate the Conda environment
SHELL ["conda", "run", "-n", "myenv", "/bin/bash", "-c"]

# 6. Check if the environment is activated properly
RUN python --version  # or conda list

# 7. Copy the rest of the application
COPY . .

# 8. Expose port 5000
EXPOSE 5000

# 9. Run the Python scripts sequentially using the Conda environment
# First, run the scripts with RUN during the build phase
RUN conda run -n myenv python src/main/python/rs_ds.py && \
    conda run -n myenv python src/main/python/rs_nn.py

# 10. Run the rs_api.py script during container start
CMD ["bash", "-c", "conda run -n myenv python src/main/python/rs_api.py"]