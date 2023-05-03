import matplotlib.pyplot as plt
import numpy as np
import json
from scipy.ndimage import gaussian_filter

with open('result/data_test/random_60x60_ac.json', 'r') as f:
    data = np.array(json.load(f))

data = gaussian_filter(data, sigma=1)
data = data.transpose()
fig, ax = plt.subplots()
im = ax.imshow(data, cmap='RdBu', vmin=-100, vmax=100)

n_steps = 7

x_values = np.linspace(1, 4, n_steps)
labels = [f"{x:.1f}" for x in x_values]
ticks = np.linspace(0, data.shape[1] - 1, n_steps).astype(int)

ax.xaxis.tick_top()
ax.xaxis.set_label_position('top')

ax.set_xticks(ticks)
ax.set_xticklabels(labels)

ax.set_yticks(ticks)
ax.set_yticklabels(labels)

plt.setp(ax.get_xticklabels(), rotation_mode="anchor")

cbar = ax.figure.colorbar(im, ax=ax)

ax.set_title("Random mode")
ax.set_xlabel("Assimilation factor")
ax.set_ylabel("Fertility factor")

plt.tight_layout()
plt.show()
